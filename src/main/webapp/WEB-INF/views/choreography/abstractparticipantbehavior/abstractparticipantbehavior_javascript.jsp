<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // abstract participant behavior
   var apb_nodesDataset, apb_edgesDataset, apb_network = null;

   function apb_initNetwork(apb_nodesArray, apb_edgesArray) {
      apb_detroyNetwork();
      apb_nodesDataset = new vis.DataSet(apb_nodesArray);
      apb_edgesDataset = new vis.DataSet(apb_edgesArray);
      var items = {
         nodes: apb_nodesDataset,
         edges: apb_edgesDataset
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
               gravitationalConstant: -2000,
               centralGravity: 0.04,
               springLength: 200,
               avoidOverlap: 1
            },
            minVelocity: 0.75,
            solver: "barnesHut"
         }
      }; // options
      apb_network = new vis.Network($("#abstractparticipantbehavior_vis_container")[0], items, options);
      apb_network.fit();

      // restore checkbox fix editor physics
      var checkboxPhysics = $('#apb-physics')[0];
      checkboxPhysics.checked = false;
      fireCheckboxChanging(checkboxPhysics);

      hideAllApbProperties();

      apb_network.on("click", function(params) {
         hideAllApbProperties();
         if (params.nodes.length === 1 && apb_network.isCluster(params.nodes[0]) === true) {
            // click on a cluster
            showApbParticipantProperty(params.nodes[0]);
         } else if (params.nodes.length === 1) {
            //click on a node
            showApbNodeProperty(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            showApbEdgeProperty(params.edges[0]);
         }
      });

      apb_network
         .on("doubleClick", function(params) {
            if (params.nodes.length == 1) {
               if (apb_network.isCluster(params.nodes[0]) == true) {
                  apb_network.openCluster(params.nodes[0]);
                  hideAllApbProperties();
               } else {
                  var participant_id = apb_nodesDataset.get(params.nodes[0]).participant;

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
                  apb_network.cluster(clusterOptionsByData);
                  hideAllApbProperties();
               }
            }
         });

      apb_network.on("stabilizationProgress", function(params) {
         //  var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
      });
      apb_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
      });
   }

   function apb_detroyNetwork() {
      if (apb_network !== null) {
         apb_network.destroy();
         apb_network = null;
      }
   }

   function hideAllApbProperties() {
      $('#abstractparticipantbehavior-participant-properties').hide();
      $('#abstractparticipantbehavior-node-properties').hide();
      $('#abstractparticipantbehavior-transition-properties').hide();
   }

   function showApbParticipantProperty(participantName) {
      $('#abstractparticipantbehavior-participant-properties').show();
      $('#property-apb-participant-name').val(participantName);
   }

   function showApbNodeProperty(nodeID) {
      $('#abstractparticipantbehavior-node-properties').show();
      $('#property-apb-state-id').val(apb_nodesDataset.get(nodeID).id);
      $('#property-apb-state-type')
         .val(visjs_locales["${pageContext.response.locale}"][apb_nodesDataset
            .get(nodeID).group
         ]);
      $('#property-apb-state-name').val(apb_nodesDataset.get(nodeID).name);
      $('#property-apb-state-participant').val(apb_nodesDataset.get(nodeID).participant);
   }

   function showApbEdgeProperty(edgeID) {
      $('#abstractparticipantbehavior-transition-properties').show();
      $('#property-apb-transition-id').val(apb_edgesDataset.get(edgeID).id);
      $('#property-apb-transition-type')
         .val(visjs_locales["${pageContext.response.locale}"][apb_edgesDataset
            .get(edgeID).type
         ]);
      $('#property-apb-transition-label')
         .val(apb_edgesDataset.get(edgeID).label);
      $('#property-apb-transition-from').val(apb_nodesDataset
         .get(apb_edgesDataset.get(edgeID).from).name);
      $('#property-apb-transition-to').val(apb_nodesDataset
         .get(apb_edgesDataset.get(edgeID).to).name);
      $('#property-apb-transition-message')
         .val(apb_edgesDataset.get(edgeID).message);
      $('#property-apb-transition-message-participant-sending')
         .val(apb_edgesDataset.get(edgeID).participantSendMessage);
      $('#property-apb-transition-message-participant-receiving')
         .val(apb_edgesDataset.get(edgeID).participantReceiveMessage);
   }

   $(document)
      .ready(function() {
         // create a apb_network
         apb_initNetwork([], []);

         $("#generateProjections")
            .click(function(e) {
               e.preventDefault();
               $
                  .ajax("${pageContext.request.contextPath}/choreography/choreographyspecification/project", {
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
                           apb_initNetwork(data.result.nodes, data.result.edges);
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

         $('#apb-physics').on("change", function(e) {
            apb_network.setOptions({
               physics: !this.checked
            });
         });
      });
</script>