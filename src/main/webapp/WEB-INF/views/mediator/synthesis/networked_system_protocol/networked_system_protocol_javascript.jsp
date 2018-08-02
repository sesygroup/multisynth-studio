<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

   <script type="text/javascript" charset="utf-8">
      // service specification
      var nsp_nodesDataset, nsp_edgesDataset, nsp_network = null;

      function nsp_initNetwork(nsp_nodesArray, nsp_edgesArray) {
         nsp_detroyNetwork();
         nsp_nodesDataset = new vis.DataSet(nsp_nodesArray);
         nsp_edgesDataset = new vis.DataSet(nsp_edgesArray);

         var items = {
            nodes: nsp_nodesDataset,
            edges: nsp_edgesDataset
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
                  $('#nsp-state-x').val(data.x);
                  $('#nsp-state-y').val(data.y);
                  $('#nsp-state-action').val('create');
                  $("#nsp-state-name").parsley().reset();
                  $("#saveNodeButton").text("<spring:message code='common.create' />")
                  $('#nsp-node-popUp-modal').modal('show');
                  callback(null);
               },
               editNode: function(data, callback) {
                  $('#nsp-state-action').val('update');
                  $('#nsp-state-id').val(data.id);
                  $("#nsp-state-name").val(nsp_nodesDataset.get(data.id).name);
                  $("#saveNodeButton").text("<spring:message code='common.update' />")
                  $("#nsp-state-name").parsley().reset();
                  $('#nsp-node-popUp-modal').modal('show');
                  callback(null);
               },
               deleteNode: function(data, callback) {

                  if (nsp_nodesDataset.get(data.nodes[0]).group == "sourceNode") {
                     callback(null);
                     showMessage("error", "<spring:message code='common.error' />", "<spring:message code='nsp.state.source.delete.error.message' />");
                  } else {
                     hideAllProperties();
                     callback(data);
                  }

               },
               addEdge: function(data, callback) {
                  $('#nsp-transition-node-from-id').val(data.from);
                  $('#nsp-transition-node-from').val(nsp_nodesDataset.get(data.from).name);
                  $('#nsp-transition-node-to-id').val(data.to);
                  $('#nsp-transition-node-to').val(nsp_nodesDataset.get(data.to).name);
                  $("#nsp-transition-action-operation").parsley().reset();
                  $('#nsp-transition-action-input-parameters').importTags('');
                  $('#nsp-transition-action-output-parameters').importTags('');
                  $('#nsp-transition-action-type').val('request-response').change();
                  $('#nsp-transition-popUp-modal').modal('show');
                  callback(null);
               },
               deleteEdge: function(data, callback) {
                  hideAllProperties();
                  callback(data);

               },
               editEdge: false
            }
         }; // options

         nsp_network = new vis.Network($("#networkedsystemprotocol_vis_container")[0], items, options);
         nsp_network.fit();

         hideAllProperties();

         nsp_network.on("click", function(params) {
            hideAllProperties();
            if (params.nodes.length === 1) {
               //click on a node
               $(".vis-manipulation").show();
               showNspNodeProperty(params.nodes[0]);
            } else if (params.edges.length === 1) {
               //click on a edge
               $(".vis-manipulation").show();
               showNspEdgeProperty(params.edges[0]);
            } else {
               // click on a network
               showNspNetworkProperty();
            }
         });

         nsp_network.on("stabilizationProgress", function(params) {
            //  var prog = params.iterations/params.total;
            // status.innerText = Math.round(prog*100)+'%';
         });
         nsp_network.on("stabilizationIterationsDone", function() {
            //status.innerText = "stabilized";
         });

      }

      function hideAllProperties() {
         $('#networkedsystemprotocol-network-properties').hide();
         $('#networkedsystemprotocol-node-properties').hide();
         $('#networkedsystemprotocol-transition-properties').hide();
      }

      function showNspNetworkProperty() {
         $('#networkedsystemprotocol-network-properties').show();
         $('#property-nsp-states-number').val(nsp_nodesDataset.get().length);
         $('#property-nsp-transitions-number').val(nsp_edgesDataset.get().length);
      }

      function showNspNodeProperty(nodeID) {
         $('#networkedsystemprotocol-node-properties').show();
         $('#property-nsp-state-id').val(nodeID);
         $('#property-nsp-state-type').val(visjs_locales["${pageContext.response.locale}"][nsp_nodesDataset.get(nodeID).group]);
         $('#property-nsp-state-name').val(nsp_nodesDataset.get(nodeID).name);
         $('#property-nsp-state-name').attr('data-parsley-state_exists_skip_selected',nodeID);
         $('#property-nsp-state-name').parsley().validate();
      }

      function showNspEdgeProperty(edgeID) {
         $('#networkedsystemprotocol-transition-properties').show();
         $('#property-nsp-transition-id').val(edgeID);
         $('#property-nsp-transition-label').val(nsp_edgesDataset.get(edgeID).label);
         $('#property-nsp-transition-from').val(nsp_nodesDataset.get(nsp_edgesDataset.get(edgeID).from).name);
         $('#property-nsp-transition-to').val(nsp_nodesDataset.get(nsp_edgesDataset.get(edgeID).to).name);
         $('#property-nsp-transition-action-operation').val(nsp_edgesDataset.get(edgeID).action.operationName);
         $('#property-nsp-transition-action-type').val(nsp_edgesDataset.get(edgeID).action.type).change();
         
         //reset all input and output before add them
         $('#property-nsp-transition-action-input-parameters').importTags('');
         $('#property-nsp-transition-action-output-parameters').importTags('');
         $.each(nsp_edgesDataset.get(edgeID).action.inputParameters,function( index, value ) {
        	 $('#property-nsp-transition-action-input-parameters').addTag(value);
         });
         $.each(nsp_edgesDataset.get(edgeID).action.outputParameters,function( index, value ) {
        	 $('#property-nsp-transition-action-output-parameters').addTag(value);
         });
      }

      function nsp_detroyNetwork() {
         if (nsp_network !== null) {
            nsp_network.destroy();
            nsp_network = null;
         }
      }

      function stateExists(stateName) {
         var exists = false;
         nsp_nodesDataset.get().forEach(function(node) {
            if (node.name == stateName) {
               exists = true;
            }
         });
         return exists;
      }

      function stateExistsSkipSelected(stateName, stateIdSkip) {
          var exists = false;
          nsp_nodesDataset.get().forEach(function(node) {
             if (node.name == stateName && node.id != stateIdSkip) {
                exists = true;
             }
          });
          return exists;
       }

      function saveNode() {
         $("#nsp-state-name").parsley().validate();
         if ($("#nsp-state-name").parsley().isValid() === true) {
            if ($('#nsp-state-action').val() === 'create') {
               nsp_nodesDataset.add({
                  x: $('#nsp-state-x').val(),
                  y: $('#nsp-state-y').val(),
                  label: $("#nsp-state-name").val(),
                  title: $("#nsp-state-name").val(),
                  name: $("#nsp-state-name").val(),
                  group: 'genericNode',
                  type: 'genericNode'
               });
               showMessage("success", "<spring:message code='common.success' />", "<spring:message code='nsp.state.create.success.message' />");
            } else {
               nsp_nodesDataset.update([{
                  id: $('#nsp-state-id').val(),
                  label: $("#nsp-state-name").val(),
                  title: $("#nsp-state-name").val(),
                  name: $("#nsp-state-name").val()
               }]);
               $('#nsp-state-id').val('');
               showMessage("success", "<spring:message code='common.success' />", "<spring:message code='nsp.state.update.success.message' />");
            }

            $('#nsp-node-popUp-modal').modal('hide');
            hideAllProperties();
         }
      }

      function saveTransition() {
         $("#nsp-transition-action-operation").parsley().validate();
         
            var label = "< " + $("#nsp-transition-action-operation").val() + ", " + "{" + $("#nsp-transition-action-input-parameters").val().replace(",", ", ") + "}, " + "{" + $("#nsp-transition-action-output-parameters").val().replace(",", ", ") + "}, " + (($("#nsp-transition-action-type").val() === "request-response" || $("#nsp-transition-action-type").val() === "one-way") ? "required" : "provided")+">";         
            if ($("#nsp-transition-action-operation").parsley().isValid() === true) {
            nsp_edgesDataset.add({
               from: $("#nsp-transition-node-from-id").val(),
               to: $("#nsp-transition-node-to-id").val(),
               label: label,
               title: "("+$("#nsp-transition-node-from").val() + ", " + label + ", " + $("#nsp-transition-node-to").val()+")",
               action: { 
            	   operationName: $("#nsp-transition-action-operation").val(),
            	   type: $("#nsp-transition-action-type").val(),
                   inputParameters: $("#nsp-transition-action-input-parameters").val().split(","),
                   outputParameters:$("#nsp-transition-action-output-parameters").val().split(",")
               },
    	       dashes: false
            });
            $('#nsp-transition-popUp-modal').modal('hide');
            hideAllProperties();
            showMessage("success", "<spring:message code='common.success' />", "<spring:message code='nsp.transition.create.success.message' />");
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
         $("#nsp-state-name").keypress(function(event) {
            if (event.which == 13) {
               event.preventDefault();
               $("#saveNodeButton").click();
            }
         });
         
         $("#property-nsp-state-name").keypress(function(event) {
             if (event.which == 13) {
                event.preventDefault();
                $("#property-nsp-state-update").click();
             }
          });

         //update state
         $("#property-nsp-state-update").click(function(e) {
             e.preventDefault();
             $('#property-nsp-state-name').parsley().validate();
             if ($('#property-nsp-state-name').parsley().isValid() === true) {
            	 nsp_nodesDataset.update([{
                     id: $('#property-nsp-state-id').val(),
                     label: $('#property-nsp-state-name').val(),
                     title: $('#property-nsp-state-name').val(),
                     name: $('#property-nsp-state-name').val()
                  }]);
            	 showMessage("success", "<spring:message code='common.success' />", "<spring:message code='nsp.state.update.success.message' />");
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
            $.ajax("${pageContext.request.contextPath}/mediator/nsp/loadblueclient", {
               type: 'POST',
               data: {},
               dataType: 'json',
               contentType: false,
               success: function(data) {
                  nsp_initNetwork(data.result.nodes, data.result.edges);
               }
            });
         });

         $("#locadMoonService").click(function(e) {
            e.preventDefault();
            $.ajax("${pageContext.request.contextPath}/mediator/nsp/loadmoonservice", {
               type: 'POST',
               data: {},
               dataType: 'json',
               contentType: false,
               success: function(data) {
                  nsp_initNetwork(data.result.nodes, data.result.edges);
               }
            });
         });
         
         $("#testMoonService")
         .click(function(e) {
            e.preventDefault();
            $
               .ajax("${pageContext.request.contextPath}/mediator/nsp/testmoonservice", {
                  type: 'POST',
                  data: JSON.stringify({
                     "nodes": nsp_nodesDataset.get(),
                     "edges": nsp_edgesDataset.get()
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
         
         
         
         
         $('#nsp-physics').on("change", function(e) {
            nsp_network.setOptions({
               physics: !this.checked
            });
         });

        
         $("#property-nsp-transition-action-type").change(function () {
        	 if ($(this).val() === "one-way"|| $(this).val() === "notification"){
        		 $("#property-nsp-transition-action-output-parameters-div").hide();
        	 }else{
        		 $("#property-nsp-transition-action-output-parameters-div").show();
        	 }
         });
         
         $("#nsp-transition-action-type").change(function () {
        	 if ($(this).val() === "one-way"|| $(this).val() === "notification"){
        		 $("#nsp-transition-action-output-parameters-div").hide();
        	 }else{
        		 $("#nsp-transition-action-output-parameters-div").show();
        	 }
         });
        
        // for input parameters of an action
        $('#property-nsp-transition-action-input-parameters').tagsInput({
        	   'height':'40px;',
        	   'width':'100%',
        	   'interactive':true,
        	   'defaultText':'<spring:message code='nsp.action.parameters.add' />',
        	   'removeWithBackspace' : true,
        	   'minChars' : 0,
        	   'maxChars' : 0, // if not provided there is no limit
        	   'placeholderColor' : '#666666'
        	});
        
        $('#nsp-transition-action-input-parameters').tagsInput({
      	   'height':'40px;',
      	   'width':'100%',
      	   'interactive':true,
      	   'defaultText':'<spring:message code='nsp.action.parameters.add' />',
      	   'removeWithBackspace' : true,
      	   'minChars' : 0,
      	   'maxChars' : 0, // if not provided there is no limit
      	   'placeholderColor' : '#666666'
      	});
        
         // Property for output parameter of an action
         $('#property-nsp-transition-action-output-parameters').tagsInput({
      	   'height':'40px;',
    	   'width':'100%',
    	   'interactive':true,
    	   'defaultText':'<spring:message code='nsp.action.parameters.add' />',
    	   'removeWithBackspace' : true,
    	   'minChars' : 0,
    	   'maxChars' : 0, // if not provided there is no limit
    	   'placeholderColor' : '#666666'
    	});
         
         $('#nsp-transition-action-output-parameters').tagsInput({
        	   'height':'40px;',
        	   'width':'100%',
        	   'interactive':true,
        	   'defaultText':'<spring:message code='nsp.action.parameters.add' />',
        	   'removeWithBackspace' : true,
        	   'minChars' : 0,
        	   'maxChars' : 0, // if not provided there is no limit
        	   'placeholderColor' : '#666666'
        	});
         
         
         

         nsp_initNetwork([], []);
         // add initial state
         nsp_nodesDataset.add({
            label: 's0',
            title: 's0',
            name: 's0',
            group: 'sourceNode',
            type: 'sourceNode'
         });

      });
   </script>