<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="selection_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="choreography.selection" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <%-- Add participant modal --%>
            <div id="cpb-participant-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="cpb-participant-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="cpb-participant-popUp-modal-label">
                           <spring:message code="choreography.participant.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-participant-name"><spring:message code="choreography.participant.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="cpb-participant-name" name="cpb-participant-name" data-parsley-participant_selection_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveCpbParticipant" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <%-- Add node modal --%>
            <div id="cpb-node-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="cpb-node-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="cpb-node-popUp-modal-label">
                           <spring:message code="choreography.state.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <input type="hidden" id="cpb-state-x" name="cpb-state-x" class="form-control" /> <input type="hidden" id="cpb-state-y" name="cpb-state-y" class="form-control" />
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-state-type"><spring:message code="choreography.state.type" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <select id="cpb-state-type" name="cpb-state-type" class="form-control" tabindex="-1">
                                 <option value="genericNode" selected="selected"><i class="fa fa-circle"></i><spring:message code="choreography.state.type.generic" /></option>
                                 <option value="sinkNode"><i class="fa fa-circle"></i><spring:message code="choreography.state.type.final" /></option>
                              </select>
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-state-name"><spring:message code="choreography.state.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="cpb-state-name" name="cpb-state-name" data-parsley-state_selection_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-state-participant"><spring:message code="choreography.participant" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <select id="cpb-state-participant" name="cpb-state-participant" class="form-control" tabindex="-1">
                              </select>
                              <div id="sel_error"></div>
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveCpbNodeButton" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <%-- Add transition modal --%>
            <div id="cpb-transition-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="cpb-transition-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="cpb-transition-popUp-modal-label">
                           <spring:message code="choreography.transition.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-transition-node-from"><spring:message code="choreography.state.source" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="cpb-transition-node-from-id" name="cpb-transition-node-from-id" /> <input type="text" id="cpb-transition-node-from" name="cpb-transition-node-from" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-transition-node-to"><spring:message code="choreography.state.target" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="cpb-transition-node-to-id" name="cpb-transition-node-to-id" /> <input type="text" id="cpb-transition-node-to" name="cpb-transition-node-to" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-transition-type"><spring:message code="choreography.transition.type" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <select id="cpb-transition-type" name="cpb-transition-type" class="form-control" tabindex="-1">
                                 <option value="internalAction"><spring:message code="choreography.transition.type.internal" /></option>
                                 <optgroup label="<spring:message code='choreography.transition.type.asynchronous' />">
                                    <option value="asynchSendAction"><spring:message code="choreography.transition.type.send" /></option>
                                    <option value="asynchReceiveAction"><spring:message code="choreography.transition.type.receive" /></option>
                                 </optgroup>
                                 <optgroup label="<spring:message code='choreography.transition.type.synchronous' />">
                                    <option value="synchSendAction"><spring:message code="choreography.transition.type.send" /></option>
                                    <option value="synchReceiveAction"><spring:message code="choreography.transition.type.receive" /></option>
                                 </optgroup>
                              </select>
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="cpb-transition-message"><spring:message code="choreography.message.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="cpb-transition-message" name="cpb-transition-message" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveCpbTransitionButton" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <div id="concreteparticiantsbehavior_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>

            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <button id="addCpbParticipant" class="btn btn-success btn-sm">
                     <spring:message code="choreography.participant.create" />
                  </button>
                  <label> <input type="checkbox" id="cpb-physics" class="form-control js-switch" /> <spring:message code="choreography.editor.lock" /> <label>
               </div>

               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="choreography.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>

                  <%-- participant property --%>
                  <div id="concreteparticiantsbehavior-participant-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.name" />
                        </p>
                        <input type="text" id="property-cpb-participant-name" name="property-cpb-participant-name" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- state property --%>
                  <div id="concreteparticiantsbehavior-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.id" />
                        </p>
                        <input type="text" id="property-cpb-state-id" name="property-cpb-state-id" class="form-control" disabled />
                     </div>
                     <%--
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.type" />
                        </p>
                        <input type="text" id="property-cpb-state-type" name="property-cpb-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.name" />
                        </p>
                        <input type="text" id="property-cpb-state-name" name="property-cpb-state-name" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant" />
                        </p>
                        <input type="text" id="property-cpb-state-participant" name="property-cpb-state-participant" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="concreteparticiantsbehavior-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.id" />
                        </p>
                        <input type="text" id="property-cpb-transition-id" name="property-cpb-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.type" />
                        </p>
                        <input type="text" id="property-cpb-transition-type" name="property-cpb-transition-type" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.label" />
                        </p>
                        <input type="text" id="property-cpb-transition-label" name="property-cpb-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-cpb-transition-from" name="property-cpb-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.target" />
                        </p>
                        <input type="text" id="property-cpb-transition-to" name="property-cpb-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.message.name" />
                        </p>
                        <input type="text" id="property-cpb-transition-message" name="property-cpb-transition-message" class="form-control" disabled />
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
               <%--<button id="loadCpbThree" class="btn btn-warning btn-sm">
                  <spring:message code="concrete.participant.behavior.load.three" />
               </button>
               --%>
               <button id="loadCpbSix" class="btn btn-success btn-sm">
                  <spring:message code="choreography.selection.select" />
               </button>
            </div>
         </div>
      </div>
   </div>
</div>