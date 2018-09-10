<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

   <script type="text/javascript" charset="utf-8">
      // service specification
      var bp_nodesDataset, bp_edgesDataset, bp_network = null;

      function bp_initNetwork(bp_nodesArray, bp_edgesArray) {
         bp_detroyNetwork();
         bp_nodesDataset = new vis.DataSet(bp_nodesArray);
         bp_edgesDataset = new vis.DataSet(bp_edgesArray);

         var items = {
            nodes: bp_nodesDataset,
            edges: bp_edgesDataset
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
                  $('#bp-state-x').val(data.x);
                  $('#bp-state-y').val(data.y);
                  $('#bp-state-action').val('create');
                  $("#bp-state-name").parsley().reset();
                  $("#saveNodeButton").text("<spring:message code='common.create' />")
                  $('#bp-node-popUp-modal').modal('show');
                  callback(null);
               },
               editNode: function(data, callback) {
                  $('#bp-state-action').val('update');
                  $('#bp-state-id').val(data.id);
                  $("#bp-state-name").val(bp_nodesDataset.get(data.id).name);
                  $("#saveNodeButton").text("<spring:message code='common.update' />")
                  $("#bp-state-name").parsley().reset();
                  $('#bp-node-popUp-modal').modal('show');
                  callback(null);
               },
               deleteNode: function(data, callback) {

                  if (bp_nodesDataset.get(data.nodes[0]).group == "sourceNode") {
                     callback(null);
                     showMessage("error", "<spring:message code='common.error' />", "<spring:message code='bp.state.source.delete.error.message' />");
                  } else {
                     hideAllProperties();
                     callback(data);
                  }

               },
               addEdge: function(data, callback) {
                  $('#bp-transition-node-from-id').val(data.from);
                  $('#bp-transition-node-from').val(bp_nodesDataset.get(data.from).name);
                  $('#bp-transition-node-to-id').val(data.to);
                  $('#bp-transition-node-to').val(bp_nodesDataset.get(data.to).name);
                  $('#bp-transition-type').val('sendAction').change();
                  $("#bp-transition-typedmessage-name").parsley().reset();
                  $("#bp-transition-typedmessage-content").parsley().reset();
                  $('#bp-transition-popUp-modal').modal('show');
                  callback(null);
               },
               deleteEdge: function(data, callback) {
                  hideAllProperties();
                  callback(data);

               },
               editEdge: false
            }
         }; // options

         bp_network = new vis.Network($("#behavioralprotocol_vis_container")[0], items, options);
         bp_network.fit();

         hideAllProperties();

         bp_network.on("click", function(params) {
            hideAllProperties();
            if (params.nodes.length === 1) {
               //click on a node
               $(".vis-manipulation").show();
               showBpNodeProperty(params.nodes[0]);
            } else if (params.edges.length === 1) {
               //click on a edge
               $(".vis-manipulation").show();
               showBpEdgeProperty(params.edges[0]);
            } else {
               // click on a network
               showBpNetworkProperty();
            }
         });

         bp_network.on("stabilizationProgress", function(params) {
            //  var prog = params.iterations/params.total;
            // status.innerText = Math.round(prog*100)+'%';
         });
         bp_network.on("stabilizationIterationsDone", function() {
            //status.innerText = "stabilized";
         });

      }

      function hideAllProperties() {
         $('#behavioralprotocol-network-properties').hide();
         $('#behavioralprotocol-node-properties').hide();
         $('#behavioralprotocol-transition-properties').hide();
      }

      function showBpNetworkProperty() {
         $('#behavioralprotocol-network-properties').show();
         $('#property-bp-states-number').val(bp_nodesDataset.get().length);
         $('#property-bp-transitions-number').val(bp_edgesDataset.get().length);
      }

      function showBpNodeProperty(nodeID) {
         $('#behavioralprotocol-node-properties').show();
         $('#property-bp-state-id').val(nodeID);
         $('#property-bp-state-type').val(visjs_locales["${pageContext.response.locale}"][bp_nodesDataset.get(nodeID).group]);
         $('#property-bp-state-name').val(bp_nodesDataset.get(nodeID).name);
         $('#property-bp-state-name').attr('data-parsley-state_exists_skip_selected',nodeID);
         $('#property-bp-state-name').parsley().validate();
      }

      function showBpEdgeProperty(edgeID) {
         $('#behavioralprotocol-transition-properties').show();
         $('#property-bp-transition-id').val(edgeID);
         $('#property-bp-transition-label').val(bp_edgesDataset.get(edgeID).label);
         $('#property-bp-transition-from').val(bp_nodesDataset.get(bp_edgesDataset.get(edgeID).from).name);
         $('#property-bp-transition-to').val(bp_nodesDataset.get(bp_edgesDataset.get(edgeID).to).name);
         $('#property-bp-transition-type').val(bp_edgesDataset.get(edgeID).type).change();
         $('#property-bp-transition-typedmessage-name').val(bp_edgesDataset.get(edgeID).typedMessageName);
         $('#property-bp-transition-typedmessage-content').val(bp_edgesDataset.get(edgeID).typedMessageContent);
      }

      function bp_detroyNetwork() {
         if (bp_network !== null) {
            bp_network.destroy();
            bp_network = null;
         }
      }

      function stateExists(stateName) {
         var exists = false;
         bp_nodesDataset.get().forEach(function(node) {
            if (node.name == stateName) {
               exists = true;
            }
         });
         return exists;
      }

      function stateExistsSkipSelected(stateName, stateIdSkip) {
          var exists = false;
          bp_nodesDataset.get().forEach(function(node) {
             if (node.name == stateName && node.id != stateIdSkip) {
                exists = true;
             }
          });
          return exists;
       }

      function saveNode() {
         $("#bp-state-name").parsley().validate();
         if ($("#bp-state-name").parsley().isValid() === true) {
            if ($('#bp-state-action').val() === 'create') {
               bp_nodesDataset.add({
                  x: $('#bp-state-x').val(),
                  y: $('#bp-state-y').val(),
                  label: $("#bp-state-name").val(),
                  title: $("#bp-state-name").val(),
                  name: $("#bp-state-name").val(),
                  group: 'genericNode',
                  type: 'genericNode'
               });
               showMessage("success", "<spring:message code='common.success' />", "<spring:message code='bp.state.create.success.message' />");
            } else {
               bp_nodesDataset.update([{
                  id: $('#bp-state-id').val(),
                  label: $("#bp-state-name").val(),
                  title: $("#bp-state-name").val(),
                  name: $("#bp-state-name").val()
               }]);
               $('#bp-state-id').val('');
               showMessage("success", "<spring:message code='common.success' />", "<spring:message code='bp.state.update.success.message' />");
            }

            $('#bp-node-popUp-modal').modal('hide');
            hideAllProperties();
         }
      }

      function saveTransition() {
    	  var label = "";
    	  if ($('#bp-transition-type').val()=== 'sendAction' || $('#bp-transition-type').val()=== 'receiveAction'){
    		  $("#bp-transition-typedmessage-name").parsley().validate();
              $("#bp-transition-typedmessage-content").parsley().validate();  
              
              if ($("#bp-transition-typedmessage-name").parsley().isValid() === true && $("#bp-transition-typedmessage-name").parsley().isValid() === true) {
            	  if ($('#bp-transition-type').val()=== 'sendAction'){
            		  label = "("+$("#bp-transition-typedmessage-name").val()+","+$("#bp-transition-typedmessage-content").val()+")!";
            	  } else if ($('#bp-transition-type').val()=== 'receiveAction'){
            		  label = "("+$("#bp-transition-typedmessage-name").val()+","+$("#bp-transition-typedmessage-content").val()+")?";  
            	  }
            	 
            	  bp_edgesDataset.add({
                     from: $("#bp-transition-node-from-id").val(),
                     to: $("#bp-transition-node-to-id").val(),
                     label: label,
                     title: "("+$("#bp-transition-node-from").val() + ", " + label + ", " + $("#bp-transition-node-to").val()+")",
                     type: $("#bp-transition-type").val(),
                     typedMessageName: $("#bp-transition-typedmessage-name").val(),
                     typedMessageContent: $("#bp-transition-typedmessage-content").val(),
                     dashes: false
                  });
                  $('#bp-transition-popUp-modal').modal('hide');
                  hideAllProperties();
                  showMessage("success", "<spring:message code='common.success' />", "<spring:message code='bp.transition.create.success.message' />");
               }
          }else{
        	  label = "epsilon"
        	  bp_edgesDataset.add({
                  from: $("#bp-transition-node-from-id").val(),
                  to: $("#bp-transition-node-to-id").val(),
                  label: label,
                  title: "("+$("#bp-transition-node-from").val() + ", " + label + ", " + $("#bp-transition-node-to").val()+")",
                  type: $("#bp-transition-type").val(),
                  typedMessageName: "",
                  typedMessageContent: "",
                  dashes: false
               });
        	  
        	  $('#bp-transition-popUp-modal').modal('hide');
              hideAllProperties();
              showMessage("success", "<spring:message code='common.success' />", "<spring:message code='bp.transition.create.success.message' />");
          }
      }


      $(document).ready(function() {
      window.Parsley.addValidator('state_exists', {
            validateString: function(value) {
               return !stateExists(value);
            }
         });
         
         window.Parsley.addValidator('state_exists_skip_selected', {
             validateString: function(value,stateIdSkip) {
                return !stateExistsSkipSelected(value,stateIdSkip);
             }
          });

         // trigger save botton when we press enter on the name of the state
         $("#bp-state-name").keypress(function(event) {
            if (event.which == 13) {
               event.preventDefault();
               $("#saveNodeButton").click();
            }
         });
         
         $("#property-bp-state-name").keypress(function(event) {
             if (event.which == 13) {
                event.preventDefault();
                $("#property-bp-state-update").click();
             }
          });

         //update state
         $("#property-bp-state-update").click(function(e) {
             e.preventDefault();
             $('#property-bp-state-name').parsley().validate();
             if ($('#property-bp-state-name').parsley().isValid() === true) {
                bp_nodesDataset.update([{
                     id: $('#property-bp-state-id').val(),
                     label: $('#property-bp-state-name').val(),
                     title: $('#property-bp-state-name').val(),
                     name: $('#property-bp-state-name').val()
                  }]);
                showMessage("success", "<spring:message code='common.success' />", "<spring:message code='bp.state.update.success.message' />");
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
            $.ajax("${pageContext.request.contextPath}/mediator/behavioralprotocol/loadblueclient", {
               type: 'POST',
               data: {},
               dataType: 'json',
               contentType: false,
               success: function(data) {
                  bp_initNetwork(data.result.nodes, data.result.edges);
               }
            });
         });

         $("#locadMoonService").click(function(e) {
             e.preventDefault();
             $.ajax("${pageContext.request.contextPath}/mediator/behavioralprotocol/loadmoonservice", {
                type: 'POST',
                data: {},
                dataType: 'json',
                contentType: false,
                success: function(data) {
                   bp_initNetwork(data.result.nodes, data.result.edges);
                }
             });
          });
         
         $("#testMoonService")
         .click(function(e) {
            e.preventDefault();
            $
               .ajax("${pageContext.request.contextPath}/mediator/behavioralprotocol/testmoonservice", {
                  type: 'POST',
                  data: JSON.stringify({
                     "nodes": bp_nodesDataset.get(),
                     "edges": bp_edgesDataset.get()
                  }),
                  dataType: 'json',
                  contentType: "application/json",
                  success: function(data) {
                     if (data.state == "SUCCESS") {
                        showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.specification.project.success.message' />");
                     } else if (data.state == "ERROR") {
                        showMessage("error", "<spring:message code='common.error' />", data.result.message);
                     }

                  },
                  error: function(XMLHttpRequest, textStatus,
                     errorThrown) {
                     console.log(textStatus);
                  }
               });
         });
         
         
         
         
         $('#bp-physics').on("change", function(e) {
            bp_network.setOptions({
               physics: !this.checked
            });
         });

         $("#property-bp-transition-type").change(function () {
             if ($(this).val() === "internalAction"){
                $("#property-bp-transition-typedmessage-div").hide();
             }else{
                $("#property-bp-transition-typedmessage-div").show();
             }
            });
            
            $("#bp-transition-type").change(function () {
             if ($(this).val() === "internalAction"){
                $("#bp-transition-typedmessage-div").hide();
             }else{
                $("#bp-transition-typedmessage-div").show();
             }
            });
        
        
         bp_initNetwork([], []);
         // add initial state
         bp_nodesDataset.add({
            label: 's0',
            title: 's0',
            name: 's0',
            group: 'sourceNode',
            type: 'sourceNode'
         });

      });
   </script>