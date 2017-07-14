<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
   // coordination delegate
   var cd_nodesDataset, cd_edgesDataset, cd_network = null;

   function cd_initNetwork(cd_nodesArray, cd_edgesArray) {
      cd_detroyNetwork();
      cd_nodesDataset = new vis.DataSet(cd_nodesArray);
      cd_edgesDataset = new vis.DataSet(cd_edgesArray);
      var items = {
         nodes: cd_nodesDataset,
         edges: cd_edgesDataset
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
      cd_network = new vis.Network($("#coordinationdelegate_vis_container")[0], items, options);
      cd_network.fit();

      // restore checkbox fix editor physics
      var checkboxPhysics = $('#cd-physics')[0];
      checkboxPhysics.checked = false;
      fireCheckboxChanging(checkboxPhysics);

      hideAllCdProperties();

      cd_network.on("click", function(params) {
         hideAllCdProperties();
         if (params.nodes.length === 1 && cd_network.isCluster(params.nodes[0]) === true) {
            // click on a cluster
            showCdParticipantProperty(params.nodes[0]);
         } else if (params.nodes.length === 1) {
            //click on a node
            showCdNodeProperty(params.nodes[0]);
         } else if (params.edges.length === 1) {
            //click on a edge
            showCdEdgeProperty(params.edges[0]);
         }
      });

      cd_network
         .on("doubleClick", function(params) {
            if (params.nodes.length == 1) {
               if (cd_network.isCluster(params.nodes[0]) == true) {
                  cd_network.openCluster(params.nodes[0]);
                  hideAllCdProperties();
               } else {
                  var participant_id = cd_nodesDataset.get(params.nodes[0]).participant;

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
                  cd_network.cluster(clusterOptionsByData);
                  hideAllCdProperties();
               }
            }
         });

      cd_network.on("stabilizationProgress", function(params) {
         //  var prog = params.iterations/params.total;
         // status.innerText = Math.round(prog*100)+'%';
      });
      cd_network.on("stabilizationIterationsDone", function() {
         //status.innerText = "stabilized";
      });
   }

   function cd_detroyNetwork() {
      if (cd_network !== null) {
         cd_network.destroy();
         cd_network = null;
      }
   }

   function hideAllCdProperties() {
      $('#coordinationdelegate-participant-properties').hide();
      $('#coordinationdelegate-node-properties').hide();
      $('#coordinationdelegate-transition-properties').hide();
   }

   function showCdParticipantProperty(participantName) {
      $('#coordinationdelegate-participant-properties').show();
      $('#property-cd-participant-name').val(participantName);
   }

   function showCdNodeProperty(nodeID) {
      $('#coordinationdelegate-node-properties').show();
      $('#property-cd-state-id').val(cd_nodesDataset.get(nodeID).id);
      $('#property-cd-state-type')
         .val(visjs_locales["${pageContext.response.locale}"][cd_nodesDataset
            .get(nodeID).group
         ]);
      $('#property-cd-state-name').val(cd_nodesDataset.get(nodeID).name);
      $('#property-cd-state-participant').val(cd_nodesDataset.get(nodeID).participant);
   }

   function showCdEdgeProperty(edgeID) {
      $('#coordinationdelegate-transition-properties').show();
      $('#property-cd-transition-id').val(cd_edgesDataset.get(edgeID).id);
      $('#property-cd-transition-type')
         .val(visjs_locales["${pageContext.response.locale}"][cd_edgesDataset
            .get(edgeID).type
         ]);
      $('#property-cd-transition-label')
         .val(cd_edgesDataset.get(edgeID).label);
      $('#property-cd-transition-from').val(cd_nodesDataset
         .get(cd_edgesDataset.get(edgeID).from).name);
      $('#property-cd-transition-to').val(cd_nodesDataset
         .get(cd_edgesDataset.get(edgeID).to).name);
      $('#property-cd-transition-message')
         .val(cd_edgesDataset.get(edgeID).message);
      $('#property-cd-transition-message-participant-sending')
         .val(cd_edgesDataset.get(edgeID).participantSendMessage);
      $('#property-cd-transition-message-participant-receiving')
         .val(cd_edgesDataset.get(edgeID).participantReceiveMessage);
   }

   $(document)
      .ready(function() {
         // create a cd_network
         cd_initNetwork([], []);
         
         $("#locadCoordinationDelegates").click(function(e) {
            e.preventDefault();
            $.ajax("${pageContext.request.contextPath}/choreography/coordinationdelegate/loadFive", {
               type: 'POST',
               data: {},
               dataType: 'json',
               contentType: false,
               success: function(data) {
                 cd_initNetwork(data.result.nodes, data.result.edges);
               }
            });
         });
         
         $("#generateCoordinationDelegates")
         .click(function(e) {
            e.preventDefault();
            $
               .ajax("${pageContext.request.contextPath}/choreography/coordinationdelegate/generate", {
                  type: 'POST',
                  data: JSON.stringify({
                     "choreographySpecification" :{
                        "nodes": cs_nodesDataset.get(),
                        "edges": cs_edgesDataset.get()
                     },
                     "concreteParticipantsBehavior" :{
                        "nodes": cpb_nodesDataset.get(),
                        "edges": cpb_edgesDataset.get()
                     }}),
                  dataType: 'json',
                  contentType: "application/json",
                  success: function(data) {
                     if (data.state == "SUCCESS") {
                        showMessage("success", "<spring:message code='common.success' />", "<spring:message code='choreography.specification.project.success.message' />");
                        cd_initNetwork(data.result.nodes, data.result.edges);
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

         $('#cd-physics').on("change", function(e) {
            cd_network.setOptions({
               physics: !this.checked
            });
         });
      });
</script>