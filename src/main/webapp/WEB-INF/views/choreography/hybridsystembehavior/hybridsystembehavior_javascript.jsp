<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // hybrid system behavior
   var hsb_nodesDataset, hsb_edgesDataset, hsb_network = null;

   function hsb_initNetwork(hsb_nodesArray, hsb_edgesArray) {
      hsb_detroyNetwork();
      hsb_nodesDataset = new vis.DataSet(hsb_nodesArray);
      hsb_edgesDataset = new vis.DataSet(hsb_edgesArray);
      var items = {
         nodes: hsb_nodesDataset,
         edges: hsb_edgesDataset
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
            shape: 'ellipse', //'dot', 
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
            smooth: {
               type: 'continuous'
            },
            selectionWidth: 5,
            labelHighlightBold: false
         },
         interaction: {
            tooltipDelay: 120,
            hover: true,
            selectConnectedEdges: false,
            navigationButtons: true
         },
         layout: {
            randomSeed: 2,
            improvedLayout:false
         },
         physics: {
            adaptiveTimestep: true,
            barnesHut: {
                gravitationalConstant: -80000,
                springConstant: 0.001,
                springLength: 200
            },
            stabilization: {
                iterations: 2000
            }
        }
         /*  physics: {
            //http://visjs.org/examples/network/physics/physicsConfiguration.html 
            stabilization: false,
            enabled: false,
            barnesHut: {
               gravitationalConstant: -80000,
               springConstant: 0.001,
               springLength: 200,
               avoidOverlap: 1
            },
            stabilization: {
              iterations: 1000,
              updateInterval: 100,
              onlyDynamicEdges: false,
              fit: true
            },
            barnesHut: {
               gravitationalConstant: -2000,
               centralGravity: 0.02,
               springLength: 200,
               avoidOverlap:1
             },
             minVelocity: 0.75,
             solver: "barnesHut"
         }*/
      }; // options
      hsb_network = new vis.Network($("#hybridsystembehavior_vis_container")[0], items, options);
      hsb_network.fit();

      // restore checkbox fix editor physics
      var checkboxPhysics = $('#hsb-physics')[0];
      checkboxPhysics.checked = false;
      fireCheckboxChanging(checkboxPhysics);

      hideAllHsbProperties();
      showHsbNetworkProperty();

      hsb_network.on("click", function(params) {
         hideAllHsbProperties();

         //click on a node
         if (params.nodes.length === 1) {
            showHsbNodeProperty(params.nodes[0]);
            /* the pathHighlight from a selected node is disabled
            because this operation can break the user interaction for huge graph*/
            //pathHighlight(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            showHsbEdgeProperty(params.edges[0]);
         } else {
            // click on a network
            showHsbNetworkProperty();
            restoreColorAllNodesAndEdges();
         }
      });

      hsb_network.on("stabilizationProgress", function(params) {
           var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
         console.log(Math.round(prog*100)+'%');
      });
      hsb_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
         console.log("stabilized");
      });
   }

   function hsb_detroyNetwork() {
      if (hsb_network !== null) {
         hsb_network.destroy();
         hsb_network = null;
      }
   }

   function hideAllHsbProperties() {
      $('#hybridsystembehavior-network-properties').hide();
      $('#hybridsystembehavior-node-properties').hide();
      $('#hybridsystembehavior-transition-properties').hide();
   }

   function showHsbNetworkProperty() {
      $('#hybridsystembehavior-network-properties').show();
      $('#property-hsb-states-number').val(hsb_nodesDataset.get().length);
      $('#property-hsb-transitions-number').val(hsb_edgesDataset.get().length);
   }

   function showHsbNodeProperty(nodeID) {
      $('#hybridsystembehavior-node-properties').show();
      $('#property-hsb-state-id').val(hsb_nodesDataset.get(nodeID).id);
      $('#property-hsb-state-type')
         .val(visjs_locales["${pageContext.response.locale}"][hsb_nodesDataset
            .get(nodeID).group
         ]);
      $('#property-hsb-state-name').val(hsb_nodesDataset.get(nodeID).name);
   }

   function showHsbEdgeProperty(edgeID) {
      $('#hybridsystembehavior-transition-properties').show();
      $('#property-hsb-transition-id').val(hsb_edgesDataset.get(edgeID).id);
      $('#property-hsb-transition-type')
         .val(visjs_locales["${pageContext.response.locale}"][hsb_edgesDataset
            .get(edgeID).type
         ]);
      $('#property-hsb-transition-label')
         .val(hsb_edgesDataset.get(edgeID).label);
      $('#property-hsb-transition-from').val(hsb_nodesDataset
         .get(hsb_edgesDataset.get(edgeID).from).name);
      $('#property-hsb-transition-to').val(hsb_nodesDataset
         .get(hsb_edgesDataset.get(edgeID).to).name);
      $('#property-hsb-transition-message')
         .val(hsb_edgesDataset.get(edgeID).message);
      $('#property-hsb-transition-message-participant-sending')
         .val(hsb_edgesDataset.get(edgeID).participantSendMessage);
      $('#property-hsb-transition-message-participant-receiving')
         .val(hsb_edgesDataset.get(edgeID).participantReceiveMessage);
   }

   function grayColorAllNodesAndEdges() {
      hsb_nodesDataset.get().forEach(function(node) {
         changeColorNode(node, 'rgba(200,200,200,0.5)');
      });

      hsb_edgesDataset.get().forEach(function(edge) {
         changeColorEdge(edge, 'rgba(200,200,200,1.0)');
      });
   }

   function restoreColorAllNodesAndEdges() {
      hsb_nodesDataset.get().forEach(function(node) {
         changeColorNode(node, undefined);
      });

      hsb_edgesDataset.get().forEach(function(edge) {
         var color = {
            color: '#0000FF',
            highlight: '#0000FF',
            hover: '#0000FF',
            opacity: 1.0
         };
         changeColorEdge(edge, color);
      });
   }

   function changeColorNode(node, color) {
      node.color = color;
      hsb_nodesDataset.update(node);
   }

   function changeColorEdge(edge, color) {
      edge.color = color;
      hsb_edgesDataset.update(edge);
   }

   function pathHighlight(startingNodeID) {

      // mark all nodes as hard to read.
      grayColorAllNodesAndEdges();

      var nodesToBeAnalyzed = [startingNodeID];
      var nodesAnalyzed = [];
      while (nodesToBeAnalyzed.length >= 1) {
         // move the node ID from the array nodesToBeAnalyzed to nodesAnalyzed;
         var nodeID = nodesToBeAnalyzed.shift();
         nodesAnalyzed.push(nodeID);
         // get the node object
         var node = hsb_nodesDataset.get(nodeID);

         // restore color for the node selected
         changeColorNode(node, undefined);

         // get all connected edges: return a list of edges id
         var connectedEdges = hsb_network.getConnectedEdges(node.id);

         // find all outgoing edge: is an outgoing when the "from" node is the currently node
         connectedEdges.forEach(function(edgeID) {
            var edge = hsb_edgesDataset.get(edgeID);
            if (edge.from == node.id) {
               // restore color for the outgoing edge
               var color = {
                  color: '#0000FF',
                  highlight: '#0000FF',
                  hover: '#0000FF',
                  opacity: 1.0
               };
               changeColorEdge(edge, color);
               // get the target node and check that is not already analyzed (used for loop path )or is in nodesToBeAnalyzed
               // $.inArray return the index of the founded element, -1 otherwise;
               if ($.inArray(edge.to, nodesToBeAnalyzed) == -1 &&
                  $.inArray(edge.to, nodesToBeAnalyzed) == -1) {
                  nodesToBeAnalyzed.push(edge.to);
               }

            }
         });

      }

   }

   function pathHighlight(startingNodeID) {

      // mark all nodes as hard to read.
      grayColorAllNodesAndEdges();

      var nodesToBeAnalyzed = [startingNodeID];
      var nodesAnalyzed = [];
      while (nodesToBeAnalyzed.length >= 1) {
         // move the node ID from the array nodesToBeAnalyzed to nodesAnalyzed;
         var nodeID = nodesToBeAnalyzed.shift();
         nodesAnalyzed.push(nodeID);
         // get the node object
         var node = hsb_nodesDataset.get(nodeID);

         // restore color for the node selected
         changeColorNode(node, undefined);

         // get all connected edges: return a list of edges id
         var connectedEdges = hsb_network.getConnectedEdges(node.id);

         // find all outgoing edge: is an outgoing when the "from" node is the currently node
         connectedEdges.forEach(function(edgeID) {
            var edge = hsb_edgesDataset.get(edgeID);
            if (edge.from == node.id) {
               // restore color for the outgoing edge
               var color = {
                  color: '#0000FF',
                  highlight: '#0000FF',
                  hover: '#0000FF',
                  opacity: 1.0
               };
               changeColorEdge(edge, color);
               // get the target node and check that is not already analyzed (used for loop path )or is in nodesToBeAnalyzed
               // $.inArray return the index of the founded element, -1 otherwise;
               if ($.inArray(edge.to, nodesToBeAnalyzed) == -1 &&
                  $.inArray(edge.to, nodesToBeAnalyzed) == -1) {
                  nodesToBeAnalyzed.push(edge.to);
               }

            }
         });

      }

   }

   function showHsbPath(obj) {
      // mark all nodes as hard to read.
      grayColorAllNodesAndEdges();

      $(obj).data().edges.forEach(function(edge) {
         changeColorNode(hsb_nodesDataset.get(edge.from), undefined);
         var color = {
            color: '#0000FF',
            highlight: '#0000FF',
            hover: '#0000FF',
            opacity: 1.0
         };
         changeColorEdge(hsb_edgesDataset.get(edge.id), color);
         changeColorNode(hsb_nodesDataset.get(edge.to), undefined);
      });
   }

   function cleanAndInsertPaths(paths) {
      var table = $('#path-list').DataTable();
      table.clear().draw(false);
      paths.forEach(function(path) {
         table.row.add([
            path.node.id,
            path.length,
            "<button name='hsbShowPath' class='btn btn-success btn-xs' data-edges='" + JSON.stringify(path.edges) + "' onclick='showHsbPath(this);' ><i class='fa fa-random'></i> <spring:message code='hybrid.system.behavior.path.view' /></button>"
         ]).draw(false);
      });
   }

   $(document)
      .ready(function() {
         // create a hsb_network
         hsb_initNetwork([], []);

         $("#generateHsb")
            .click(function(e) {
               e.preventDefault();
               $
                  .ajax("${pageContext.request.contextPath}/choreography/hybridsystembehavior/generate", {
                     type: 'POST',
                     data: JSON.stringify({
                        "nodes": cpb_nodesDataset.get(),
                        "edges": cpb_edgesDataset.get()
                     }),
                     dataType: 'json',
                     contentType: "application/json",
                     success: function(data) {
                        if (data.state == "SUCCESS") {
                           showMessage("success", "<spring:message code='common.success' />", "<spring:message code='hybrid.system.behavior.generate.success.message' />");
                           hsb_initNetwork(data.result.nodes, data.result.edges);
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

         $("#hsbFindReachability")
            .click(function(e) {
               e.preventDefault();
               if (hsb_network.getSelectedNodes()[0] === undefined) {
                  showMessage("error", "<spring:message code='common.error' />", "<spring:message code='hybrid.system.behavior.find.reachability.selection.state.error.message' />");
                  return;
               }
               $
                  .ajax("${pageContext.request.contextPath}/choreography/hybridsystembehavior/find/reachability?targetNode=" + hsb_network.getSelectedNodes()[0], {
                     type: 'POST',
                     data: JSON.stringify({
                        "nodes": hsb_nodesDataset.get(),
                        "edges": hsb_edgesDataset.get()
                     }),
                     dataType: 'json',
                     contentType: "application/json",
                     success: function(data) {
                        if (data.state == "SUCCESS") {
                           showMessage("success", "<spring:message code='common.success' />", "<spring:message code='hybrid.system.behavior.find.reachability.success.message' />");
                           cleanAndInsertPaths(data.result);
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


         $('#path-list').dataTable({
            "destroy": true,
            "searching": false,
            "oLanguage": {
               "sUrl": "${pageContext.request.contextPath}/static/plugin_extension/datatables/i18n/datatables-${pageContext.response.locale}.properties"
            }
         });

         $('#hsb-physics').on("change", function(e) {
            hsb_network.setOptions({
               physics: !this.checked
            });
         });
      });
</script>