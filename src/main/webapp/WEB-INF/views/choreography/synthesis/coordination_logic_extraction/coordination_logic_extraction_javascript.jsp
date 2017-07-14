<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // abstract coordination logic extraction
   var coordinationlogicextraction_nodesDataset, coordinationlogicextraction_edgesDataset, coordinationlogicextraction_network = null;

   function coordinationlogicextraction_initNetwork(coordinationlogicextraction_nodesArray, coordinationlogicextraction_edgesArray) {
      coordinationlogicextraction_detroyNetwork();
      coordinationlogicextraction_nodesDataset = new vis.DataSet(coordinationlogicextraction_nodesArray);
      coordinationlogicextraction_edgesDataset = new vis.DataSet(coordinationlogicextraction_edgesArray);
      var items = {
         nodes: coordinationlogicextraction_nodesDataset,
         edges: coordinationlogicextraction_edgesDataset
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
            labelHighlightBold: false
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
               gravitationalConstant: -4000,
               centralGravity: 0.04,
               springLength: 400,
               avoidOverlap: 1
            },
            minVelocity: 0.75,
            solver: "barnesHut"
         }
      }; // options
      coordinationlogicextraction_network = new vis.Network($("#coordinationlogicextraction_vis_container")[0], items, options);
      coordinationlogicextraction_network.fit();

      // restore checkbox fix editor physics
      var checkboxPhysics = $('#coordinationlogicextraction-physics')[0];
      checkboxPhysics.checked = false;
      fireCheckboxChanging(checkboxPhysics);

      hideAllAbstractCdLogicProperties();

      coordinationlogicextraction_network.on("click", function(params) {
         hideAllAbstractCdLogicProperties();
         if (params.nodes.length === 1 && coordinationlogicextraction_network.isCluster(params.nodes[0]) === true) {
            // click on a cluster
            showAllAbstractCdLogicParticipantProperty(params.nodes[0]);
         } else if (params.nodes.length === 1) {
            //click on a node
            showAllAbstractCdLogicNodeProperty(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            showAllAbstractCdLogicEdgeProperty(params.edges[0]);
         }
      });

      coordinationlogicextraction_network
         .on("doubleClick", function(params) {
            if (params.nodes.length == 1) {
               if (coordinationlogicextraction_network.isCluster(params.nodes[0]) == true) {
                  coordinationlogicextraction_network.openCluster(params.nodes[0]);
                  hideAllAbstractCdLogicProperties();
               } else {
                  var participant_id = coordinationlogicextraction_nodesDataset.get(params.nodes[0]).participant;

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
                  coordinationlogicextraction_network.cluster(clusterOptionsByData);
                  hideAllAbstractCdLogicProperties();
               }
            }
         });

      coordinationlogicextraction_network.on("stabilizationProgress", function(params) {
         //  var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
      });
      coordinationlogicextraction_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
      });
   }

   function coordinationlogicextraction_detroyNetwork() {
      if (coordinationlogicextraction_network !== null) {
         coordinationlogicextraction_network.destroy();
         coordinationlogicextraction_network = null;
      }
   }

   function hideAllAbstractCdLogicProperties() {
      $('#coordinationlogicextraction-participant-properties').hide();
      $('#coordinationlogicextraction-node-properties').hide();
      $('#coordinationlogicextraction-transition-properties').hide();
   }

   function showAllAbstractCdLogicParticipantProperty(participantName) {
      $('#coordinationlogicextraction-participant-properties').show();
      $('#property-coordinationlogicextraction-participant-name').val(participantName);
   }

   function showAllAbstractCdLogicNodeProperty(nodeID) {
      $('#coordinationlogicextraction-node-properties').show();
      $('#property-coordinationlogicextraction-state-id').val(coordinationlogicextraction_nodesDataset.get(nodeID).id);
      $('#property-coordinationlogicextraction-state-type')
         .val(visjs_locales["${pageContext.response.locale}"][coordinationlogicextraction_nodesDataset
            .get(nodeID).group
         ]);
      $('#property-coordinationlogicextraction-state-name').val(coordinationlogicextraction_nodesDataset.get(nodeID).name);
      $('#property-coordinationlogicextraction-state-participant').val(coordinationlogicextraction_nodesDataset.get(nodeID).participant);
   }

   function showAllAbstractCdLogicEdgeProperty(edgeID) {
      $('#coordinationlogicextraction-transition-properties').show();
      $('#property-coordinationlogicextraction-transition-id').val(coordinationlogicextraction_edgesDataset.get(edgeID).id);
      $('#property-coordinationlogicextraction-transition-type')
         .val(visjs_locales["${pageContext.response.locale}"][coordinationlogicextraction_edgesDataset
            .get(edgeID).type
         ]);
      $('#property-coordinationlogicextraction-transition-label')
         .val(coordinationlogicextraction_edgesDataset.get(edgeID).label);
      $('#property-coordinationlogicextraction-transition-from').val(coordinationlogicextraction_nodesDataset
         .get(coordinationlogicextraction_edgesDataset.get(edgeID).from).name);
      $('#property-coordinationlogicextraction-transition-to').val(coordinationlogicextraction_nodesDataset
         .get(coordinationlogicextraction_edgesDataset.get(edgeID).to).name);
      $('#property-coordinationlogicextraction-transition-message')
         .val(coordinationlogicextraction_edgesDataset.get(edgeID).message);
      $('#property-coordinationlogicextraction-transition-message-participant-sending')
         .val(coordinationlogicextraction_edgesDataset.get(edgeID).participantSendMessage);
      $('#property-coordinationlogicextraction-transition-message-participant-receiving')
         .val(coordinationlogicextraction_edgesDataset.get(edgeID).participantReceiveMessage);
   }

   $(document)
      .ready(function() {
         // create a coordinationlogicextraction_network
         coordinationlogicextraction_initNetwork([], []);
         
         $("#locadAbstractCoordinationLogic").click(function(e) {
            e.preventDefault();
            $.ajax("${pageContext.request.contextPath}/choreography/abstractcoordinationlogic/loadFive", {
               type: 'POST',
               data: {},
               dataType: 'json',
               contentType: false,
               success: function(data) {
                 coordinationlogicextraction_initNetwork(data.result.nodes, data.result.edges);
               }
            });
         });
         
         $("#extractCoordinationLogic")
         .click(function(e) {
            e.preventDefault();
            $
               .ajax("${pageContext.request.contextPath}/choreography/abstractcoordinationlogic/extract", {
                  type: 'POST',
                  data: JSON.stringify({
                     "nodes": cs_nodesDataset.get(),
                     "edges": cs_edgesDataset.get()
                  }),
                  dataType: 'json',
                  contentType: "application/json",
                  success: function(data) {
                     if (data.state == "SUCCESS") {
                        showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.specification.project.success.message' />");
                        coordinationlogicextraction_initNetwork(data.result.nodes, data.result.edges);
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

         $('#coordinationlogicextraction-physics').on("change", function(e) {
            coordinationlogicextraction_network.setOptions({
               physics: !this.checked
            });
         });
      });
</script>