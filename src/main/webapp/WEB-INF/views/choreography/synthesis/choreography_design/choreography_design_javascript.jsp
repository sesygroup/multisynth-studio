<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // choreography specification
   var cs_nodesDataset, cs_edgesDataset, cs_network = null;

   function cs_initNetwork(cs_nodesArray, cs_edgesArray) {
      cs_detroyNetwork();
      cs_nodesDataset = new vis.DataSet(cs_nodesArray);
      cs_edgesDataset = new vis.DataSet(cs_edgesArray);

      var items = {
         nodes: cs_nodesDataset,
         edges: cs_edgesDataset
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
               $('#cs-state-x').val(data.x);
               $('#cs-state-y').val(data.y);
               $("#cs-state-name").parsley().reset();
               $('#cs-node-popUp-modal').modal('show');
               callback(null);
            },
            deleteNode: function(data, callback) {
               //check if there exist two or more nodes that has the participant of the selected node. In that case
               // whether the node is a start state must not be deleted
               var existOtherStatesForParticipant = false;
               cs_nodesDataset.get().forEach(function(node) {
                  if (data.nodes[0] != node.id &&
                     cs_nodesDataset.get(data.nodes[0]).participant == node.participant) {
                     existOtherStatesForParticipant = true;
                  }
               });

               if (cs_nodesDataset.get(data.nodes[0]).group == "sourceNode" && existOtherStatesForParticipant === true) {
                  callback(null);
                  showMessage("error", "<spring:message code='common.error' />", "<spring:message code='concrete.participant.behavior.state.source.delete.error.message' />");
               } else {
                  hideAllCsProperties();
                  callback(data);
               }

            },
            addEdge: function(data, callback) {
               // warning if we don't have any participants
               if (cs_getParticipantNames().length != 0) {
                  $('#cs-transition-node-from-id').val(data.from);
                  $('#cs-transition-node-from').val(cs_nodesDataset.get(data.from).name);
                  $('#cs-transition-node-to-id').val(data.to);
                  $('#cs-transition-node-to').val(cs_nodesDataset.get(data.to).name);
   
                  var lastSelectedParticipantSending = $('#cs-transition-message-participant-sending').val();
                  var lastSelectedParticipantReceiving = $('#cs-transition-message-participant-receiving').val();
                  var participantNames = cs_getParticipantNames();
                  //$.inArray return the index of the founded element, -1 otherwise;
                  if ($.inArray(lastSelectedParticipantSending, participantNames) === -1) {
                     lastSelectedParticipantSending = null;
                  }
                  if ($.inArray(lastSelectedParticipantReceiving, participantNames) === -1) {
                     lastSelectedParticipantReceiving = null;
                  }
   
                  $('#cs-transition-message-participant-sending').html("");
                  $('#cs-transition-message-participant-receiving').html("");
   
                  $.each(participantNames, function(key, value) {
                     if (lastSelectedParticipantSending === null && key === 0) {
                        lastSelectedParticipantSending = value;
                     }
   
                     if (lastSelectedParticipantReceiving === null && key === 0) {
                        lastSelectedParticipantReceiving = value;
                     }
   
                     $('#cs-transition-message-participant-sending').append($("<option></option>").attr("value", value).text(value));
                     $('#cs-transition-message-participant-receiving').append($("<option></option>").attr("value", value).text(value));
   
                  });
   
                  $('#cs-transition-message-participant-sending').val(lastSelectedParticipantSending).trigger("change");
                  $('#cs-transition-message-participant-receiving').val(lastSelectedParticipantReceiving).trigger("change");
                  $("#cs-transition-message").parsley().reset();
                  $('#cs-transition-popUp-modal').modal('show');
               } else {
                  showMessage("warning", "<spring:message code='common.caution' />", "<spring:message code='choreography.specification.transition.create.modal.show.error.message' />");
               }
               callback(null);
            },
            deleteEdge: function(data, callback) {
               hideAllCsProperties();
               callback(data);

            },
            editEdge: false
         }
      }; // options

      cs_network = new vis.Network($("#choreographyspecification_vis_container")[0], items, options);
      cs_network.fit();

      hideAllCsProperties();

      cs_network.on("click", function(params) {
         hideAllCsProperties();
         if (params.nodes.length === 1) {
            //click on a node
            $(".vis-manipulation").show();
            showCsNodeProperty(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            $(".vis-manipulation").show();
            showCsEdgeProperty(params.edges[0]);
         } else {
            // click on a network
            showCsNetworkProperty();
         }
      });

      cs_network.on("stabilizationProgress", function(params) {
         //  var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
      });
      cs_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
      });

   }

   function hideAllCsProperties() {
      $('#choreographyspecification-network-properties').hide();
      $('#choreographyspecification-node-properties').hide();
      $('#choreographyspecification-transition-properties').hide();
   }

   function showCsNetworkProperty() {
      $('#choreographyspecification-network-properties').show();
      $('#property-cs-states-number').val(cs_nodesDataset.get().length);
      $('#property-cs-transitions-number').val(cs_edgesDataset.get().length);
   }

   function showCsNodeProperty(nodeID) {
      $('#choreographyspecification-node-properties').show();
      $('#property-cs-state-id').val(cs_nodesDataset.get(nodeID).id);
      $('#property-cs-state-type').val(visjs_locales["${pageContext.response.locale}"][cs_nodesDataset.get(nodeID).group]);
      $('#property-cs-state-name').val(cs_nodesDataset.get(nodeID).name);
   }

   function showCsEdgeProperty(edgeID) {
      $('#choreographyspecification-transition-properties').show();
      $('#property-cs-transition-id').val(cs_edgesDataset.get(edgeID).id);
      $('#property-cs-transition-label').val(cs_edgesDataset.get(edgeID).label);
      $('#property-cs-transition-from').val(cs_nodesDataset.get(cs_edgesDataset.get(edgeID).from).name);
      $('#property-cs-transition-to').val(cs_nodesDataset.get(cs_edgesDataset.get(edgeID).to).name);
      $('#property-cs-transition-message').val(cs_edgesDataset.get(edgeID).message);
      $('#property-cs-transition-message-participant-sending').val(cs_edgesDataset.get(edgeID).participantSendMessage);
      $('#property-cs-transition-message-participant-receiving').val(cs_edgesDataset.get(edgeID).participantReceiveMessage);
   }

   function cs_detroyNetwork() {
      if (cs_network !== null) {
         cs_network.destroy();
         cs_network = null;
      }
   }

   function cs_participantExists(participantName) {
      var exists = false;
      var table = $('#participants-list').DataTable();
      table.rows().every(function(rowIdx, tableLoop, rowLoop) {
         if (this.data()[0] == participantName) {
            exists = true;
         }
      });
      return exists;
   }

   function cs_transitionExists(sourceNode, targetNode, type, message) {
      var exists = false;
      cs_edgesDataset.get().forEach(function(edge) {
         if (edge.from === sourceNode &&
            edge.to === targetNode &&
            edge.type === type &&
            edge.message === message) {
            exists = true;
         }
      });

      return exists;
   }

   function cs_stateExists(stateName) {
      var exists = false;
      cs_nodesDataset.get().forEach(function(node) {
         if (node.name == stateName) {
            exists = true;
         }
      });

      return exists;
   }

   function cs_getParticipantNames() {
      var nodeIds = cs_nodesDataset.getIds();
      var participants = [];

      var table = $('#participants-list').DataTable();
      table.rows().every(function(rowIdx, tableLoop, rowLoop) {
         // if the participant name is not added to the list
         if (participants.indexOf(this.data()[0]) == -1) {
            participants.push(this.data()[0]);
         }
      });

      return participants;
   }

   function deleteCsParticipant(obj) {
      // check if the participant can be removed.
      var participantToRemove = $(obj).data().participant;
      var remove = true;
      cs_edgesDataset.get().forEach(function(edge) {
         if (edge.participantSendMessage == participantToRemove || edge.participantReceiveMessage == participantToRemove) {
            remove = false;
         }
      });

      if (remove) {
         var table = $('#participants-list').DataTable();
         table.rows().every(function(rowIdx, tableLoop, rowLoop) {
            if (this.data()[0] == participantToRemove) {
               this.remove().draw(false);
            }
         });
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.participant.remove.success.message' />");
      } else {
         showMessage("error", "<spring:message code='common.error' />", "<spring:message code='choreography.specification.participant.remove.error.message' />");
      }
   }

   function saveCsParticipant() {
      $("#cs-participant-name").parsley().validate();

      if ($("#cs-participant-name").parsley().isValid() === true) {
         var table = $('#participants-list').DataTable();
         table.row.add([$("#cs-participant-name").val(),
            "<button name='csRemoveParticipant' class='btn btn-danger btn-xs' data-participant='" + $("#cs-participant-name").val() + "' onclick='deleteCsParticipant(this);'><i class='fa fa-trash'></i> <spring:message code='choreography.participant.remove' /></button>"
         ]).draw(false);

         $("#cs-participant-popUp-modal").modal('hide');
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.participant.create.success.message' />");
      }
   }

   function saveCsNode() {
      $("#cs-state-name").parsley().validate();
      if ($("#cs-state-name").parsley().isValid() === true) {
         cs_nodesDataset.add({
            x: $('#cs-state-x').val(),
            y: $('#cs-state-y').val(),
            label: $("#cs-state-name").val(),
            title: $("#cs-state-name").val(),
            name: $("#cs-state-name").val(),
            group: 'genericNode',
            type: 'genericNode'
         });

         $('#cs-node-popUp-modal').modal('hide');
         hideAllCsProperties();
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.state.create.success.message' />");
      }
   }

   function saveCsTransition() {
      $("#cs-transition-message").parsley().validate();

      if ($("#cs-transition-message").parsley().isValid() === true) {
         cs_edgesDataset.add({
            from: $("#cs-transition-node-from-id").val(),
            to: $("#cs-transition-node-to-id").val(),
            label: $("#cs-transition-message").val() + " " + $("#cs-transition-message-participant-sending").val() + "->" + $("#cs-transition-message-participant-receiving").val(),
            title: $("#cs-transition-message").val() + " " + $("#cs-transition-message-participant-sending").val() + "->" + $("#cs-transition-message-participant-receiving").val(),
            type: $("#cs-transition-type").val(),
            dashes: false,
            message: $("#cs-transition-message").val(),
            participantSendMessage: $("#cs-transition-message-participant-sending").val(),
            participantReceiveMessage: $("#cs-transition-message-participant-receiving").val()
         });
         $('#cs-transition-popUp-modal').modal('hide');
         hideAllCsProperties();
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.transition.create.success.message' />");
      }
   }


   $(document).ready(function() {

      window.Parsley.addValidator('participant_cs_exists', {
         validateString: function(value) {
            return !cs_participantExists(value);
         }
      });

      window.Parsley.addValidator('state_cs_exists', {
         validateString: function(value) {
            return !cs_stateExists(value);
         }
      });


      $("#addCsParticipant").click(function(e) {
         e.preventDefault();
         $("#cs-participant-name").parsley().reset();
         $("#cs-participant-popUp-modal").modal('show');
      });

      $("#saveCsParticipant").click(function(e) {
         e.preventDefault();
         saveCsParticipant();
      });

      $("#saveCsNodeButton").click(function(e) {
         e.preventDefault();
         saveCsNode();
      });

      $("#saveCsTransitionButton").click(function(e) {
         e.preventDefault();
         saveCsTransition();
      });

      $("#locadChoreographySpecification").click(function(e) {
         e.preventDefault();
         $.ajax("${pageContext.request.contextPath}/choreography/choreographyspecification/loadsample", {
            type: 'POST',
            data: {},
            dataType: 'json',
            contentType: false,
            success: function(data) {
               // remove participants list
               var table = $('#participants-list').DataTable();
               table.rows().every(function(rowIdx, tableLoop, rowLoop) {
                  this.remove().draw(false);
               });
               // populate participants list  
               data.result.edges.forEach(function(edges) {
                  if (!(cs_participantExists(edges.participantSendMessage))) {
                     table.row.add([edges.participantSendMessage,
                        "<button name='csRemoveParticipant' class='btn btn-danger btn-xs' data-participant='" + edges.participantSendMessage + "' onclick='deleteCsParticipant(this);'><i class='fa fa-trash'></i> <spring:message code='choreography.participant.remove' /></button>"
                     ]).draw(false);
                  }
                  
                  if (!(cs_participantExists(edges.participantReceiveMessage))) {
                     table.row.add([edges.participantReceiveMessage,
                        "<button name='csRemoveParticipant' class='btn btn-danger btn-xs' data-participant='" + edges.participantReceiveMessage + "' onclick='deleteCsParticipant(this);'><i class='fa fa-trash'></i> <spring:message code='choreography.participant.remove' /></button>"
                     ]).draw(false);
                  }
               });
               cs_initNetwork(data.result.nodes, data.result.edges);
            }
         });
      });

      $('#cs-physics').on("change", function(e) {
         cs_network.setOptions({
            physics: !this.checked
         });
      });

      $("#cs-transition-message-participant-sending").select2({
         escapeMarkup: function(markup) {
            return markup;
         },
         minimumResultsForSearch: Infinity
      });

      $("#cs-transition-message-participant-receiving").select2({
         escapeMarkup: function(markup) {
            return markup;
         },
         minimumResultsForSearch: Infinity
      });

      $('#participants-list').dataTable({
         "destroy": true,
         "searching": false,
         "oLanguage": {
            "sUrl": "${pageContext.request.contextPath}/static/plugin_extension/datatables/i18n/datatables-${pageContext.response.locale}.properties"
         }
      });

      cs_initNetwork([], []);
      // add initial state
      cs_nodesDataset.add({
         label: 's0',
         title: 's0',
         name: 's0',
         group: 'sourceNode',
         type: 'sourceNode'
      });

   });
</script>