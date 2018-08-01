<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="networked_system_protocol_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="nsp.design" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <%-- Add node modal --%>
            <div id="nsp-node-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="nsp-node-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="nsp-node-popUp-modal-label">
                           <spring:message code="nsp.state.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                     	<input type="hidden" id="nsp-state-id" name="nsp-state-y" class="form-control" />
                        <input type="hidden" id="nsp-state-x" name="nsp-state-x" class="form-control" />
                        <input type="hidden" id="nsp-state-y" name="nsp-state-y" class="form-control" />
                        <input type="hidden" id="nsp-state-action" name="nsp-state-y" class="form-control" />
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="nsp-state-name"><spring:message code="nsp.state.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="nsp-state-name" name="nsp-state-name" data-parsley-state_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
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
            <div id="nsp-transition-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="nsp-transition-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="nsp-transition-popUp-modal-label">
                           <spring:message code="nsp.transition.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="nsp-transition-node-from"><spring:message code="nsp.state.source" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="nsp-transition-node-from-id" name="nsp-transition-node-from-id" />
                              <input type="text" id="nsp-transition-node-from" name="nsp-transition-node-from" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="nsp-transition-node-to"><spring:message code="nsp.state.target" /></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="nsp-transition-node-to-id" name="nsp-transition-node-to-id" />
                              <input type="text" id="nsp-transition-node-to" name="nsp-transition-node-to" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="nsp-transition-message"><spring:message code="nsp.message.name" /><span class="required">*</span></label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="nsp-transition-message" name="nsp-transition-message" data-parsley-trigger="focusout" required="required" class="form-control" />
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

            <div id="networkedsystemprotocol_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>

            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label> <input type="checkbox" id="nsp-physics" class="form-control js-switch" /> <spring:message code="mediator.editor.lock" /> </label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="mediator.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- network property --%>
                  <div id="networkedsystemprotocol-network-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="nsp.states.number" />
                        </p>
                        <input type="text" id="property-nsp-states-number" name="property-nsp-states-number" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="nsp.transitions.number" />
                        </p>
                        <input type="text" id="property-nsp-transitions-number" name="property-nsp-transitions-number" class="form-control" disabled />
                     </div>
                  </div>
                  <%-- state property --%>
                  <div id="networkedsystemprotocol-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="nsp.state.id" />
                        </p>
                        <input type="text" id="property-nsp-state-id" name="property-nsp-state-id" class="form-control" disabled />
                     </div>
                     <%--
                     <div class="property">
                        <p>
                           <spring:message code="nsp.state.type" />
                        </p>
                        <input type="text" id="property-nsp-state-type" name="property-nsp-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="nsp.state.name" />
                        </p>
                        <input type="text" id="property-nsp-state-name" name="property-nsp-state-name" data-parsley-state_exists_skip_selected="ciao" data-parsley-trigger="focusout" required="required" class="form-control" />
                     </div>
                     <div class="property">
                     	<p></p>
                        <button id="property-nsp-state-update" type="button" class="btn btn-primary">
                           <spring:message code="common.update" />
                        </button>
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="networkedsystemprotocol-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="nsp.transition.id" />
                        </p>
                        <input type="text" id="property-nsp-transition-id" name="property-nsp-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="nsp.transition.label" />
                        </p>
                        <input type="text" id="property-nsp-transition-label" name="property-nsp-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="nsp.state.source" />
                        </p>
                        <input type="text" id="property-nsp-transition-from" name="property-nsp-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="nsp.state.target" />
                        </p>
                        <input type="text" id="property-nsp-transition-to" name="property-nsp-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="nsp.message.name" />
                        </p>
                        <input type="text" id="property-nsp-transition-message" name="property-nsp-transition-message" class="form-control" disabled />
                     </div>
                     
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
               <button id="locadBlueClient" class="btn btn-warning btn-sm">
                  <spring:message code="nsp.load.blueclient" />
               </button>
               <button id="locadMoonService" class="btn btn-warning btn-sm">
                  <spring:message code="nsp.load.moonservice" />
               </button>
            </div>
         </div>
      </div>
   </div>
</div>