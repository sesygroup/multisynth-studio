<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="choreographyspecification_javascript.jsp"%>
<%@include file="abstractparticipantbehavior_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="margin_top_20">
         <div class="x_panel">
            <div class="x_title">
               <h2>
                  <spring:message code="choreography.participants" />
               </h2>
               <ul class="nav navbar-right panel_toolbox">
                  <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
               </ul>
               <div class="clearfix"></div>
            </div>
            <div class="x_content">
               <div class="text-right">
                  <button id="addCsParticipant" class="btn btn-success btn-sm">
                     <spring:message code="choreography.participant.create" />
                  </button>
               </div>
               <table id="participants-list" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                  <thead>
                     <tr>
                        <th><spring:message code="choreography.participant.name" /></th>
                        <th><spring:message code="common.actions" /></th>
                     </tr>
                  </thead>
                  <tbody>
                  </tbody>
               </table>
            </div>
         </div>
      </div>

      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="choreography.specification" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <%-- Add participant modal --%>
            <div id="cs-participant-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="cs-participant-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="cs-participant-popUp-modal-label">
                           <spring:message code="choreography.participant.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-participant-name"><spring:message code="choreography.participant.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="cs-participant-name" name="cs-participant-name" data-parsley-participant_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveCsParticipant" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <%-- Add node modal --%>
            <div id="cs-node-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="cs-node-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="cs-node-popUp-modal-label">
                           <spring:message code="choreography.state.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <input type="hidden" id="cs-state-x" name="cs-state-x" class="form-control" />
                        <input type="hidden" id="cs-state-y" name="cs-state-y" class="form-control" />
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-state-name"><spring:message code="choreography.state.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="cs-state-name" name="cs-state-name" data-parsley-state_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveCsNodeButton" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <%-- Add transition modal --%>
            <div id="cs-transition-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="cs-transition-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="cs-transition-popUp-modal-label">
                           <spring:message code="choreography.transition.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-transition-node-from"><spring:message code="choreography.state.source" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="cs-transition-node-from-id" name="cs-transition-node-from-id" />
                              <input type="text" id="cs-transition-node-from" name="cs-transition-node-from" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-transition-node-to"><spring:message code="choreography.state.target" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="cs-transition-node-to-id" name="cs-transition-node-to-id" />
                              <input type="text" id="cs-transition-node-to" name="cs-transition-node-to" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-transition-message"><spring:message code="choreography.message.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="cs-transition-message" name="cs-transition-message" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-transition-message-participant-sending"><spring:message code="choreography.participant.sending.message" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <select id="cs-transition-message-participant-sending" name="cs-transition-message-participant-sending" class="form-control" tabindex="-1">
                              </select>
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cs-transition-message-participant-receiving"><spring:message code="choreography.participant.receiving.message" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <select id="cs-transition-message-participant-receiving" name="cs-transition-message-participant-receiving" class="form-control" tabindex="-1">
                              </select>
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveCsTransitionButton" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <div id="choreographyspecification_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>

            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label> <input type="checkbox" id="cs-physics" class="form-control js-switch" /> <spring:message code="choreography.editor.lock" /> <label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="choreography.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- network property --%>
                  <div id="choreographyspecification-network-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.states.number" />
                        </p>
                        <input type="text" id="property-cs-states-number" name="property-cs-states-number" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transitions.number" />
                        </p>
                        <input type="text" id="property-cs-transitions-number" name="property-cs-transitions-number" class="form-control" disabled />
                     </div>
                  </div>
                  <%-- state property --%>
                  <div id="choreographyspecification-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.id" />
                        </p>
                        <input type="text" id="property-cs-state-id" name="property-cs-state-id" class="form-control" disabled />
                     </div>
                     <%--
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.type" />
                        </p>
                        <input type="text" id="property-cs-state-type" name="property-cs-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.name" />
                        </p>
                        <input type="text" id="property-cs-state-name" name="property-cs-state-name" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="choreographyspecification-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.id" />
                        </p>
                        <input type="text" id="property-cs-transition-id" name="property-cs-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.label" />
                        </p>
                        <input type="text" id="property-cs-transition-label" name="property-cs-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-cs-transition-from" name="property-cs-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.target" />
                        </p>
                        <input type="text" id="property-cs-transition-to" name="property-cs-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.message.name" />
                        </p>
                        <input type="text" id="property-cs-transition-message" name="property-cs-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.sending.message" />
                        </p>
                        <input type="text" id="property-cs-transition-message-participant-sending" name="property-cs-transition-message-participant-sending" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.receiving.message" />
                        </p>
                        <input type="text" id="property-cs-transition-message-participant-receiving" name="property-cs-transition-message-participant-receiving" class="form-control" disabled />
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
               <button id="locadChoreographySpecification" class="btn btn-warning btn-sm">
                  <spring:message code="choreography.specification.load.sample" />
               </button>
               <button id="generateProjections" class="btn btn-success btn-sm">
                  <spring:message code="abstract.participant.behavior.generate" />
               </button>
            </div>
         </div>
      </div>
   </div>
</div>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="abstract.participant.behavior" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <div id="abstractparticipantbehavior_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>
            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label> <input type="checkbox" id="apb-physics" class="form-control js-switch" /> <spring:message code="choreography.editor.lock" /> <label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="choreography.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- participant property --%>
                  <div id="abstractparticipantbehavior-participant-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.name" />
                        </p>
                        <input type="text" id="property-apb-participant-name" name="property-apb-participant-name" class="form-control" disabled />
                     </div>
                  </div>
                  
                  <%-- node property --%>
                  <div id="abstractparticipantbehavior-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.id" />
                        </p>
                        <input type="text" id="property-apb-state-id" name="property-apb-state-id" class="form-control" disabled />
                     </div>
                     <%-- 
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.type" />
                        </p>
                        <input type="text" id="property-apb-state-type" name="property-apb-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.name" />
                        </p>
                        <input type="text" id="property-apb-state-name" name="property-apb-state-name" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant" />
                        </p>
                        <input type="text" id="property-apb-state-participant" name="property-apb-state-participant" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="abstractparticipantbehavior-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.id" />
                        </p>
                        <input type="text" id="property-apb-transition-id" name="property-apb-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.type" />
                        </p>
                        <input type="text" id="property-apb-transition-type" name="property-apb-transition-type" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.label" />
                        </p>
                        <input type="text" id="property-apb-transition-label" name="property-apb-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-apb-transition-from" name="property-apb-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-apb-transition-to" name="property-apb-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.message.name" />
                        </p>
                        <input type="text" id="property-apb-transition-message" name="property-apb-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.sending.message" />
                        </p>
                        <input type="text" id="property-apb-transition-message-participant-sending" name="property-apb-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.receiving.message" />
                        </p>
                        <input type="text" id="property-apb-transition-message-participant-receiving" name="property-apb-transition-message" class="form-control" disabled />
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>

         </div>
      </div>
   </div>
</div>