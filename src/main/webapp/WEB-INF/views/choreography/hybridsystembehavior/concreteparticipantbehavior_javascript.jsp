<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // concrete participant behavior
   var cpb_nodesDataset, cpb_edgesDataset, cpb_network = null;

   function cpb_initNetwork(cpb_nodesArray, cpb_edgesArray) {
      cpb_detroyNetwork();
      cpb_nodesDataset = new vis.DataSet(cpb_nodesArray);
      cpb_edgesDataset = new vis.DataSet(cpb_edgesArray);

      var items = {
         nodes: cpb_nodesDataset,
         edges: cpb_edgesDataset
      };
      var options = {
         groups: {
            genericNode: {
               color: {
                  border: '#2B7CE9',
                  background: '#97C2FC',
                  highlight: {
                     border: '#2B7CE9',
                     background: '#D2E5FF'
                  },
                  hover: {
                     border: '#2B7CE9',
                     background: '#D2E5FF'
                  }
               }
            },
            sourceNode: {
               color: {
                  border: '#33CC33',
                  background: '#ADEBAD',
                  highlight: {
                     border: '#33CC33',
                     background: '#D6F5D6'
                  },
                  hover: {
                     border: '#33CC33',
                     background: '#D6F5D6'
                  }
               }
            },
            sinkNode: {
               color: {
                  border: '#E92929',
                  background: '#FD9898',
                  highlight: {
                     border: '#E92929',
                     background: '#FFD2D2'
                  },
                  hover: {
                     border: '#E92929',
                     background: '#FFD2D2'
                  }
               }
            }
         },
         nodes: {
            shape: 'ellipse',
            shadow: true,
            size: 60,
            font: {
               size: 30,
               face: 'verdana'
            }
         },
         edges: {
            color: {
               color: '#0000FF',
               highlight: '#0000FF',
               hover: '#0000FF',
               opacity: 1.0
            },
            arrows: {
               to: {
                  enabled: true
               }
            },
            arrowStrikethrough: false,
            width: 0.15,
            font: {
               size: 30,
               face: 'verdana'
            },
            selectionWidth: 5,
            labelHighlightBold: true //doesn't works
         },
         interaction: {
            tooltipDelay: 120,
            hover: true,
            selectConnectedEdges: false,
            navigationButtons: true
         },
         layout: {
            randomSeed: 2
         },
         physics: {
            barnesHut: {
               gravitationalConstant: -2000,
               centralGravity: 0.04,
               springLength: 200,
               avoidOverlap: 1
            },
            minVelocity: 0.75,
            solver: "barnesHut"
         },
         locale: '${pageContext.response.locale}',
         locales: visjs_locales,
         autoResize: true,
         manipulation: {
            addNode: function(data, callback) {
               var participantNames = getParticipantNames();
               // warning if we don't have any participants
               if (participantNames.length != 0) {
                  var lastSelectedParticipant = $('#cpb-state-participant').val();
                  //$.inArray return the index of the founded element, -1 otherwise;
                  if ($.inArray(lastSelectedParticipant, participantNames) === -1) {
                     lastSelectedParticipant = null;
                  }

                  $('#cpb-state-participant').html("");
                  $.each(participantNames, function(key, value) {
                     if (lastSelectedParticipant === null && key === 0) {
                        lastSelectedParticipant = value;
                     }
                     $('#cpb-state-participant')
                        .append($("<option></option>")
                           .attr("value", value)
                           .text(value));
                  });

                  $('#cpb-state-x').val(data.x);
                  $('#cpb-state-y').val(data.y);
                  $('#cpb-state-participant').val(lastSelectedParticipant).trigger("change");
                  $("#cpb-state-name").parsley().reset();
                  $('#cpb-node-popUp-modal').modal('show');
               } else {
                  showMessage("warning", "<spring:message code='common.caution' />", "<spring:message code='concrete.participant.behavior.state.create.modal.show.error.message' />");
               }
               callback(null);
            },
            deleteNode: function(data, callback) {
               //check if there exist two or more nodes that has the participant of the selected node. In that case
               // whether the node is a start state must not be deleted
               var existOtherStatesForParticipant = false;
               cpb_nodesDataset.get().forEach(function(node) {
                  if (data.nodes[0] != node.id &&
                     cpb_nodesDataset.get(data.nodes[0]).participant == node.participant) {
                     existOtherStatesForParticipant = true;
                  }
               });

               if (cpb_nodesDataset.get(data.nodes[0]).group == "sourceNode" && existOtherStatesForParticipant === true) {
                  callback(null);
                  showMessage("error", "<spring:message code='common.error' />", "<spring:message code='concrete.participant.behavior.state.source.delete.error.message' />");
               } else {
                  hideAllCpbProperties();
                  callback(data);
               }

            },
            addEdge: function(data, callback) {
               if (cpb_network.isCluster(data.from) === true) {
                  showMessage("warning", "<spring:message code='common.caution' />", "<spring:message code='concrete.participant.behavior.transition.from.cluster.error.message' />");
                  // cannot create transition from a cluster
                  callback(null);
                  return;
               }
               if (cpb_network.isCluster(data.to) === true) {
                  showMessage("warning", "<spring:message code='common.caution' />", "<spring:message code='concrete.participant.behavior.transition.to.cluster.error.message' />");
                  // cannot create transition to a cluster
                  callback(null);
                  return;
               }

               if (cpb_nodesDataset.get(data.from).participant !== cpb_nodesDataset
                  .get(data.to).participant) {
                  // cannot create a transition between different participant nodes
                  showMessage("warning", "<spring:message code='common.caution' />", "<spring:message code='concrete.participant.behavior.transition.to.different.participant.node.error.message' />");
                  callback(null);
                  return;
               }

               $('#cpb-transition-node-from-id').val(data.from);
               $('#cpb-transition-node-from').val(cpb_nodesDataset.get(data.from).name);
               $('#cpb-transition-node-to-id').val(data.to);
               $('#cpb-transition-node-to').val(cpb_nodesDataset.get(data.to).name);

               if ($('#cpb-transition-type').find("option:selected")[0].value == "internalAction") {
                  $('#cpb-transition-message').prop("disabled", true);
                  $('#cpb-transition-message').prop("required", false);
               } else {
                  $('#cpb-transition-message').prop("disabled", false);
                  $('#cpb-transition-message').prop("required", true);
               }

               $("#cpb-transition-message").parsley().reset();
               $('#cpb-transition-popUp-modal').modal('show');
               callback(null);
            },
            deleteEdge: function(data, callback) {
               hideAllCpbProperties();
               callback(data);

            },
            editEdge: false
         }
      }; // options

      cpb_network = new vis.Network($("#concreteparticiantsbehavior_vis_container")[0], items, options);
      cpb_network.fit();

      hideAllCpbProperties();

      cpb_network.on("click", function(params) {
         hideAllCpbProperties();
         if (params.nodes.length === 1 && cpb_network.isCluster(params.nodes[0]) === true) {
            // click on a cluster
            $(".vis-manipulation").hide();
            showCpbParticipantProperty(params.nodes[0]);
         } else if (params.nodes.length === 1) {
            //click on a node
            $(".vis-manipulation").show();
            showCpbNodeProperty(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            $(".vis-manipulation").show();
            showCpbEdgeProperty(params.edges[0]);
         }
      });

      cpb_network
         .on("doubleClick", function(params) {
            if (params.nodes.length == 1) {
               if (cpb_network.isCluster(params.nodes[0]) == true) {
                  cpb_network.openCluster(params.nodes[0]);
                  hideAllCpbProperties();
               } else {
                  var participant_id = cpb_nodesDataset.get(params.nodes[0]).participant;
                  // force open the cluster, if case of already exists
                  if (cpb_network.isCluster(participant_id) === true) {
                     cpb_network.openCluster(participant_id);
                  }

                  var clusterOptionsByData = {
                     joinCondition: function(childOptions) {
                        return childOptions.participant == participant_id;
                     },
                     clusterNodeProperties: {
                        allowSingleNodeCluster: true,
                        id: participant_id,
                        label: participant_id,
                        title: participant_id,
                        widthConstraint: {
                           minimum: 100
                        },
                        heightConstraint: {
                           minimum: 100
                        },
                        borderWidth: 1,
                        shape: 'box',
                        shadow: true,
                        color: {
                           border: '#33CC33',
                           background: '#ADEBAD',
                           highlight: {
                              border: '#33CC33',
                              background: '#D6F5D6'
                           },
                           hover: {
                              border: '#33CC33',
                              background: '#D6F5D6'
                           }
                        }
                     }
                  };
                  cpb_network.cluster(clusterOptionsByData);
                  $(".vis-manipulation").hide();
                  hideAllCpbProperties();
               }
            }
         });


      cpb_network.on("stabilizationProgress", function(params) {
         //  var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
      });
      cpb_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
      });

   }

   function hideAllCpbProperties() {
      $('#concreteparticiantsbehavior-participant-properties').hide();
      $('#concreteparticiantsbehavior-node-properties').hide();
      $('#concreteparticiantsbehavior-transition-properties').hide();
   }

   function showCpbParticipantProperty(participantName) {
      $('#concreteparticiantsbehavior-participant-properties').show();
      $('#property-cpb-participant-name').val(participantName);
   }

   function showCpbNodeProperty(nodeID) {
      $('#concreteparticiantsbehavior-node-properties').show();
      $('#property-cpb-state-id').val(cpb_nodesDataset.get(nodeID).id);
      $('#property-cpb-state-type').val(visjs_locales["${pageContext.response.locale}"][cpb_nodesDataset.get(nodeID).group]);
      $('#property-cpb-state-name').val(cpb_nodesDataset.get(nodeID).name);
      $('#property-cpb-state-participant').val(cpb_nodesDataset.get(nodeID).participant);
   }

   function showCpbEdgeProperty(edgeID) {
      $('#concreteparticiantsbehavior-transition-properties').show();
      $('#property-cpb-transition-id').val(cpb_edgesDataset.get(edgeID).id);
      $('#property-cpb-transition-type').val(visjs_locales["${pageContext.response.locale}"][cpb_edgesDataset.get(edgeID).type]);
      $('#property-cpb-transition-label').val(cpb_edgesDataset.get(edgeID).label);
      $('#property-cpb-transition-from').val(cpb_nodesDataset.get(cpb_edgesDataset.get(edgeID).from).name);
      $('#property-cpb-transition-to').val(cpb_nodesDataset.get(cpb_edgesDataset.get(edgeID).to).name);
      $('#property-cpb-transition-message').val(cpb_edgesDataset.get(edgeID).message);
   }

   function cpb_detroyNetwork() {
      if (cpb_network !== null) {
         cpb_network.destroy();
         cpb_network = null;
      }
   }

   function participantExists(participantName) {
      var exists = false;
      cpb_nodesDataset.get().forEach(function(node) {
         if (node.participant === participantName) {
            exists = true;
         }
      });

      return exists;
   }

   function transitionExists(sourceNode, targetNode, type, message) {
      var exists = false;
      cpb_edgesDataset.get().forEach(function(edge) {
         if (edge.from === sourceNode &&
            edge.to === targetNode &&
            edge.type === type &&
            edge.message === message) {
            exists = true;
         }
      });

      return exists;
   }


   function stateExists(stateName, participantName) {
      var exists = false;
      cpb_nodesDataset.get().forEach(function(node) {
         if (node.name == stateName &&
            node.participant == participantName) {
            exists = true;
         }
      });

      return exists;
   }

   function getParticipantNames() {
      var nodeIds = cpb_nodesDataset.getIds();
      var participants = [];
      cpb_nodesDataset.get().forEach(function(node) {
         // if the participant name is not added to the list
         if (participants.indexOf(node.participant) == -1) {
            participants.push(node.participant);
         }
      });

      return participants;
   }

   function saveCpbParticipant() {
      $("#cpb-participant-name").parsley().validate();
      var participantName = $("#cpb-participant-name").val();
      if ($("#cpb-participant-name").parsley().isValid() === true) {
         cpb_nodesDataset.add({
            label: "s0",
            title: "s0",
            name: "s0",
            group: "sourceNode",
            type: "sourceNode",
            participant: participantName
         });

         $("#cpb-participant-popUp-modal").modal('hide');
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.participant.create.success.message' />");
      }
   }

   function saveCpbNode() {
      $("#cpb-state-name").parsley().validate();
      if ($("#cpb-state-name").parsley().isValid() === true) {
         cpb_nodesDataset.add({
            x: $('#cpb-state-x').val(),
            y: $('#cpb-state-y').val(),
            label: $("#cpb-state-name").val(),
            title: $("#cpb-state-name").val(),
            name: $("#cpb-state-name").val(),
            group: $("#cpb-state-type").val(),
            type: $("#cpb-state-type").val(),
            participant: $("#cpb-state-participant").val()
         });

         $('#cpb-node-popUp-modal').modal('hide');
         hideAllCpbProperties();
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.state.create.success.message' />");
      }
   }

   function saveCpbTransition() {
      $("#cpb-transition-message").parsley().validate();
      var transitionDashes = $("#cpb-transition-type").val().indexOf("asynch") !== -1;
      var transitionMessage = $("#cpb-transition-type").val() == "internalAction" ? "" : $("#cpb-transition-message").val();

      if ($("#cpb-transition-message").parsley().isValid() === true) {
         if (transitionExists($("#cpb-transition-node-from-id").val(), $("#cpb-transition-node-to-id").val(), $("#cpb-transition-type").val(), transitionMessage)) {
            showMessage("error", "<spring:message code='common.error' />", "<spring:message code='choreography.transition.create.already.exists.error.message' />");
         } else {
            var transitionLabel = "";
            var transitionTitle = ""
            switch ($("#cpb-transition-type").val()) {
               case 'internalAction':
                  transitionLabel = "epsilon";
                  transitionTitle = "(" + cpb_nodesDataset.get($("#cpb-transition-node-from-id").val()).name + ", " + transitionLabel + ", " + cpb_nodesDataset.get($("#cpb-transition-node-to-id").val()).name + ")";
                  break;
               case 'asynchSendAction':
                  transitionLabel = transitionMessage + "!";
                  transitionTitle = "(" + cpb_nodesDataset.get($("#cpb-transition-node-from-id").val()).name + ", " + transitionLabel + ", " + cpb_nodesDataset.get($("#cpb-transition-node-to-id").val()).name + ", asynchronous)";
                  break;
               case 'asynchReceiveAction':
                  transitionLabel = transitionMessage + "?";
                  transitionTitle = "(" + cpb_nodesDataset.get($("#cpb-transition-node-from-id").val()).name + ", " + transitionLabel + ", " + cpb_nodesDataset.get($("#cpb-transition-node-to-id").val()).name + ", asynchronous)";
                  break;
               case 'synchSendAction':
                  transitionLabel = transitionMessage + "!";
                  transitionTitle = "(" + cpb_nodesDataset.get($("#cpb-transition-node-from-id").val()).name + ", " + transitionLabel + ", " + cpb_nodesDataset.get($("#cpb-transition-node-to-id").val()).name + ", synchronous)";
                  break;
               case 'synchReceiveAction':
                  transitionLabel = transitionMessage + "?";
                  transitionTitle = "(" + cpb_nodesDataset.get($("#cpb-transition-node-from-id").val()).name + ", " + transitionLabel + ", " + cpb_nodesDataset.get($("#cpb-transition-node-to-id").val()).name + ", synchronous)";
                  break;
            }

            var addedEdge = cpb_edgesDataset.add({
               from: $("#cpb-transition-node-from-id").val(),
               to: $("#cpb-transition-node-to-id").val(),
               label: transitionLabel,
               title: transitionTitle,
               type: $("#cpb-transition-type").val(),
               dashes: transitionDashes,
               message: transitionMessage
            });
            $('#cpb-transition-popUp-modal').modal('hide');
            hideAllCpbProperties();
            showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.transition.create.success.message' />");
         }
      }
   }

   $(document).ready(function() {

      window.Parsley.addValidator('participant_exists', {
         validateString: function(value) {
            return !participantExists(value);
         }
      });

      window.Parsley.addValidator('state_exists', {
         validateString: function(value) {
            return !stateExists(value, $("#cpb-state-participant").val());
         }
      });

      $("#addCpbParticipant").click(function(e) {
         e.preventDefault();
         $("#cpb-participant-name").parsley().reset();
         $("#cpb-participant-popUp-modal").modal('show');
      });

      $("#saveCpbParticipant").click(function(e) {
         e.preventDefault();
         saveCpbParticipant();
      });

      $("#saveCpbNodeButton").click(function(e) {
         e.preventDefault();
         saveCpbNode();
      });

      $("#saveCpbTransitionButton").click(function(e) {
         e.preventDefault();
         saveCpbTransition();
      });

      $("#loadCpbThree").click(function(e) {
         e.preventDefault();
         $.ajax("${pageContext.request.contextPath}/choreography/concreteparticipantbehavior/loadthree", {
            type: 'POST',
            data: {},
            dataType: 'json',
            contentType: false,
            success: function(data) {
               cpb_initNetwork(data.result.nodes, data.result.edges);
            }
         });
      });

      $("#loadCpbSix").click(function(e) {
         e.preventDefault();
         $.ajax("${pageContext.request.contextPath}/choreography/concreteparticipantbehavior/loadsix", {
            type: 'POST',
            data: {},
            dataType: 'json',
            contentType: false,
            success: function(data) {
               cpb_initNetwork(data.result.nodes, data.result.edges);
            }
         });
      });

      $('#cpb-state-type').select2({
         escapeMarkup: function(markup) {
            return markup;
         },
         minimumResultsForSearch: Infinity
      });

      $("#cpb-state-participant").select2({
         escapeMarkup: function(markup) {
            return markup;
         },
         minimumResultsForSearch: Infinity
      });

      $('#cpb-transition-type').select2({
         escapeMarkup: function(markup) {
            return markup;
         },
         minimumResultsForSearch: Infinity
      });

      $('#cpb-physics').on("change", function(e) {
         cpb_network.setOptions({
            physics: !this.checked
         });
      });

      $('#cpb-transition-type').on("change", function(e) {
         if ($(e.currentTarget).find("option:selected")[0].value == "internalAction") {
            $('#cpb-transition-message').prop("disabled", true);
            $('#cpb-transition-message').prop("required", false);
         } else {
            $('#cpb-transition-message').prop("disabled", false);
            $('#cpb-transition-message').prop("required", true);
         }
      });



      cpb_initNetwork([], []);

   });
</script>