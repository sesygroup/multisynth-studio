<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="service_design_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="service.design" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <%-- Add node modal --%>
            <div id="ss-node-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="ss-node-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="ss-node-popUp-modal-label">
                           <spring:message code="service.state.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <input type="hidden" id="ss-state-x" name="ss-state-x" class="form-control" />
                        <input type="hidden" id="ss-state-y" name="ss-state-y" class="form-control" />
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="ss-state-name"><spring:message code="service.state.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="ss-state-name" name="ss-state-name" data-parsley-state_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveNodeButton" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <%-- Add transition modal --%>
            <div id="ss-transition-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="ss-transition-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="ss-transition-popUp-modal-label">
                           <spring:message code="service.transition.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="ss-transition-node-from"><spring:message code="service.state.source" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="ss-transition-node-from-id" name="ss-transition-node-from-id" />
                              <input type="text" id="ss-transition-node-from" name="ss-transition-node-from" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="ss-transition-node-to"><spring:message code="service.state.target" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="ss-transition-node-to-id" name="ss-transition-node-to-id" />
                              <input type="text" id="ss-transition-node-to" name="ss-transition-node-to" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="ss-transition-message"><spring:message code="service.message.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="ss-transition-message" name="ss-transition-message" data-parsley-trigger="focusout" required="required" class="form-control" />
                           </div>
                        </div>
                     </div>
                     <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                           <spring:message code="common.close" />
                        </button>
                        <button id="saveTransitionButton" type="button" class="btn btn-primary">
                           <spring:message code="common.create" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>

            <div id="servicespecification_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>

            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label> <input type="checkbox" id="ss-physics" class="form-control js-switch" /> <spring:message code="mediator.editor.lock" /> </label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="mediator.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- network property --%>
                  <div id="servicespecification-network-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="service.states.number" />
                        </p>
                        <input type="text" id="property-ss-states-number" name="property-ss-states-number" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="service.transitions.number" />
                        </p>
                        <input type="text" id="property-ss-transitions-number" name="property-ss-transitions-number" class="form-control" disabled />
                     </div>
                  </div>
                  <%-- state property --%>
                  <div id="servicespecification-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="service.state.id" />
                        </p>
                        <input type="text" id="property-ss-state-id" name="property-ss-state-id" class="form-control" disabled />
                     </div>
                     <%--
                     <div class="property">
                        <p>
                           <spring:message code="service.state.type" />
                        </p>
                        <input type="text" id="property-ss-state-type" name="property-ss-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="service.state.name" />
                        </p>
                        <input type="text" id="property-ss-state-name" name="property-ss-state-name" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="servicespecification-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="service.transition.id" />
                        </p>
                        <input type="text" id="property-ss-transition-id" name="property-ss-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="service.transition.label" />
                        </p>
                        <input type="text" id="property-ss-transition-label" name="property-ss-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="service.state.source" />
                        </p>
                        <input type="text" id="property-ss-transition-from" name="property-ss-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="service.state.target" />
                        </p>
                        <input type="text" id="property-ss-transition-to" name="property-ss-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="service.message.name" />
                        </p>
                        <input type="text" id="property-ss-transition-message" name="property-ss-transition-message" class="form-control" disabled />
                     </div>
                     
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
               <button id="locadBlueClient" class="btn btn-warning btn-sm">
                  <spring:message code="service.load.blueclient" />
               </button>
               <button id="locadMoonService" class="btn btn-warning btn-sm">
                  <spring:message code="service.load.moonservice" />
               </button>
            </div>
         </div>
      </div>
   </div>
</div>