<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // service specification
   var ss_nodesDataset, ss_edgesDataset, ss_network = null;

   function ss_initNetwork(ss_nodesArray, ss_edgesArray) {
      ss_detroyNetwork();
      ss_nodesDataset = new vis.DataSet(ss_nodesArray);
      ss_edgesDataset = new vis.DataSet(ss_edgesArray);

      var items = {
         nodes: ss_nodesDataset,
         edges: ss_edgesDataset
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
               $('#ss-state-x').val(data.x);
               $('#ss-state-y').val(data.y);
               $("#ss-state-name").parsley().reset();
               $('#ss-node-popUp-modal').modal('show');
               callback(null);
            },
            editNode: function(data, callback) {
            	
            },
            deleteNode: function(data, callback) {
               
               if (ss_nodesDataset.get(data.nodes[0]).group == "sourceNode") {
                  callback(null);
                  showMessage("error", "<spring:message code='common.error' />", "<spring:message code='service.state.source.delete.error.message' />");
               } else {
                  hideAllProperties();
                  callback(data);
               }

            },
            addEdge: function(data, callback) {
                  $('#ss-transition-node-from-id').val(data.from);
                  $('#ss-transition-node-from').val(ss_nodesDataset.get(data.from).name);
                  $('#ss-transition-node-to-id').val(data.to);
                  $('#ss-transition-node-to').val(ss_nodesDataset.get(data.to).name);
   
                  $("#ss-transition-message").parsley().reset();
                  $('#ss-transition-popUp-modal').modal('show');
               
               callback(null);
            },
            deleteEdge: function(data, callback) {
               hideAllProperties();
               callback(data);

            },
            editEdge: false
         }
      }; // options

      ss_network = new vis.Network($("#servicespecification_vis_container")[0], items, options);
      ss_network.fit();

      hideAllProperties();

      ss_network.on("click", function(params) {
         hideAllProperties();
         if (params.nodes.length === 1) {
            //click on a node
            $(".vis-manipulation").show();
            showSsNodeProperty(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            $(".vis-manipulation").show();
            showSsEdgeProperty(params.edges[0]);
         } else {
            // click on a network
            showSsNetworkProperty();
         }
      });

      ss_network.on("stabilizationProgress", function(params) {
         //  var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
      });
      ss_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
      });

   }

   function hideAllProperties() {
      $('#servicespecification-network-properties').hide();
      $('#servicespecification-node-properties').hide();
      $('#servicespecification-transition-properties').hide();
   }

   function showSsNetworkProperty() {
      $('#servicespecification-network-properties').show();
      $('#property-ss-states-number').val(ss_nodesDataset.get().length);
      $('#property-ss-transitions-number').val(ss_edgesDataset.get().length);
   }

   function showSsNodeProperty(nodeID) {
      $('#servicespecification-node-properties').show();
      $('#property-ss-state-id').val(ss_nodesDataset.get(nodeID).id);
      $('#property-ss-state-type').val(visjs_locales["${pageContext.response.locale}"][ss_nodesDataset.get(nodeID).group]);
      $('#property-ss-state-name').val(ss_nodesDataset.get(nodeID).name);
   }

   function showSsEdgeProperty(edgeID) {
      $('#servicespecification-transition-properties').show();
      $('#property-ss-transition-id').val(ss_edgesDataset.get(edgeID).id);
      $('#property-ss-transition-label').val(ss_edgesDataset.get(edgeID).label);
      $('#property-ss-transition-from').val(ss_nodesDataset.get(ss_edgesDataset.get(edgeID).from).name);
      $('#property-ss-transition-to').val(ss_nodesDataset.get(ss_edgesDataset.get(edgeID).to).name);
      $('#property-ss-transition-message').val(ss_edgesDataset.get(edgeID).message);
   }

   function ss_detroyNetwork() {
      if (ss_network !== null) {
         ss_network.destroy();
         ss_network = null;
      }
   }


   function ss_transitionExists(sourceNode, targetNode, type, message) {
      var exists = false;
      ss_edgesDataset.get().forEach(function(edge) {
         if (edge.from === sourceNode &&
            edge.to === targetNode &&
            edge.type === type &&
            edge.message === message) {
            exists = true;
         }
      });

      return exists;
   }

   function ss_stateExists(stateName) {
      var exists = false;
      ss_nodesDataset.get().forEach(function(node) {
         if (node.name == stateName) {
            exists = true;
         }
      });

      return exists;
   }

   



   function saveNode() {
      $("#ss-state-name").parsley().validate();
      if ($("#ss-state-name").parsley().isValid() === true) {
         ss_nodesDataset.add({
            x: $('#ss-state-x').val(),
            y: $('#ss-state-y').val(),
            label: $("#ss-state-name").val(),
            title: $("#ss-state-name").val(),
            name: $("#ss-state-name").val(),
            group: 'genericNode',
            type: 'genericNode'
         });

         $('#ss-node-popUp-modal').modal('hide');
         hideAllProperties();
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='service.state.create.success.message' />");
      }
   }

   function saveTransition() {
      $("#ss-transition-message").parsley().validate();

      if ($("#ss-transition-message").parsley().isValid() === true) {
         ss_edgesDataset.add({
            from: $("#ss-transition-node-from-id").val(),
            to: $("#ss-transition-node-to-id").val(),
            label: $("#ss-transition-message").val() + " " + $("#ss-transition-message-participant-sending").val() + "->" + $("#ss-transition-message-participant-receiving").val(),
            title: $("#ss-transition-message").val() + " " + $("#ss-transition-message-participant-sending").val() + "->" + $("#ss-transition-message-participant-receiving").val(),
            type: $("#ss-transition-type").val(),
            dashes: false,
            message: $("#ss-transition-message").val()
         });
         $('#ss-transition-popUp-modal').modal('hide');
         hideAllProperties();
         showMessage("success", "<spring:message code='common.success' />", "<spring:message code='service.transition.create.success.message' />");
      }
   }


   $(document).ready(function() {

      window.Parsley.addValidator('state_exists', {
         validateString: function(value) {
            return !ss_stateExists(value);
         }
      });


      $("#saveNodeButton").click(function(e) {
         e.preventDefault();
         saveNode();
      });

      $("#saveTransitionButton").click(function(e) {
         e.preventDefault();
         saveTransition();
      });

      $("#locadBlueClient").click(function(e) {
         e.preventDefault();
         $.ajax("${pageContext.request.contextPath}/mediator/service/loadblueclient", {
            type: 'POST',
            data: {},
            dataType: 'json',
            contentType: false,
            success: function(data) {
               ss_initNetwork(data.result.nodes, data.result.edges);
            }
         });
      });
      
      $("#locadMoonService").click(function(e) {
          e.preventDefault();
          $.ajax("${pageContext.request.contextPath}/mediator/service/loadmoonservice", {
             type: 'POST',
             data: {},
             dataType: 'json',
             contentType: false,
             success: function(data) {
                ss_initNetwork(data.result.nodes, data.result.edges);
             }
          });
       });
      $('#ss-physics').on("change", function(e) {
         ss_network.setOptions({
            physics: !this.checked
         });
      });

    

      ss_initNetwork([], []);
      // add initial state
      ss_nodesDataset.add({
         label: 's0',
         title: 's0',
         name: 's0',
         group: 'sourceNode',
         type: 'sourceNode'
      });

   });
</script>