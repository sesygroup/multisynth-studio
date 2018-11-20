<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="behavioral_protocol_javascript.jsp"%>
<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="bp.design" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <%-- Add node modal --%>
            <div id="bp-node-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="bp-node-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="bp-node-popUp-modal-label">
                           <spring:message code="bp.state.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <input type="hidden" id="bp-state-id" name="bp-state-id" class="form-control" />
                        <input type="hidden" id="bp-state-x" name="bp-state-x" class="form-control" />
                        <input type="hidden" id="bp-state-y" name="bp-state-y" class="form-control" />
                        <input type="hidden" id="bp-state-action" name="bp-state-action" class="form-control" />
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="bp-state-name">
                              <spring:message code="bp.state.name" />
                              <span class="required">*</span>
                           </label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="text" id="bp-state-name" name="bp-state-name" data-parsley-state_exists="" data-parsley-trigger="focusout" required="required" class="form-control" />
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
            <div id="bp-transition-popUp-modal" class="modal fade" tabindex="-1" aria-labelledby="bp-transition-popUp-modal-label" role="dialog" aria-hidden="true">
               <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                           <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="bp-transition-popUp-modal-label">
                           <spring:message code="bp.transition.create" />
                        </h4>
                     </div>
                     <div class="modal-body">
                        <input type="hidden" id="bp-transition-id" name="bp-transition-id" class="form-control" />
                        <input type="hidden" id="bp-transition-action" name="bp-transition-action" class="form-control" />
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="bp-transition-node-from">
                              <spring:message code="bp.state.source" />
                           </label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="bp-transition-node-from-id" name="bp-transition-node-from-id" />
                              <input type="text" id="bp-transition-node-from" name="bp-transition-node-from" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="bp-transition-node-to">
                              <spring:message code="bp.state.target" />
                           </label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <input type="hidden" id="bp-transition-node-to-id" name="bp-transition-node-to-id" />
                              <input type="text" id="bp-transition-node-to" name="bp-transition-node-to" class="form-control" disabled />
                           </div>
                        </div>
                        <div class="row">
                           <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="bp-transition-type">
                              <spring:message code="bp.transition.type" />
                           </label>
                           <div class="col-md-9 col-sm-12 col-xs-12">
                              <select id="bp-transition-type" name="bp-transition-type" class="form-control" tabindex="-1">
                                 <option value="sendAction">
                                    <spring:message code="bp.transition.type.send" />
                                 </option>
                                 <option value="receiveAction">
                                    <spring:message code="bp.transition.type.receive" />
                                 </option>
                                 <option value="internalAction">
                                    <spring:message code="bp.transition.type.internal" />
                                 </option>
                              </select>
                           </div>
                        </div>
                        <div id="bp-transition-typedmessage-div">
                           <div class="row">
                              <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="bp-transition-typedmessage-name">
                                 <spring:message code="bp.transition.typedmessage.name" />
                                 <span class="required">*</span>
                              </label>
                              <div class="col-md-9 col-sm-12 col-xs-12">
                                 <input type="text" id="bp-transition-typedmessage-name" name="bp-transition-typedmessage-name" data-parsley-trigger="focusout" required="required" class="form-control" />
                              </div>
                           </div>
                           <div class="row">
                              <label class="col-md-3 col-sm-12 col-xs-12 text-md-right text-sm-left text-xs-left" for="bp-transition-typedmessage-content">
                                 <spring:message code="bp.transition.typedmessage.content" />
                                 <span class="required">*</span>
                              </label>
                              <div class="col-md-9 col-sm-12 col-xs-12">
                                 <input type="text" id="bp-transition-typedmessage-content" name="bp-transition-typedmessage-content" data-parsley-trigger="focusout" required="required" class="form-control" />
                              </div>
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
            <div id="behavioralprotocol_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>
            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label>
                     <input type="checkbox" id="bp-physics" class="form-control js-switch" />
                     <spring:message code="mediator.editor.lock" />
                  </label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="mediator.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- network property --%>
                  <div id="behavioralprotocol-network-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="bp.states.number" />
                        </p>
                        <input type="text" id="property-bp-states-number" name="property-bp-states-number" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="bp.transitions.number" />
                        </p>
                        <input type="text" id="property-bp-transitions-number" name="property-bp-transitions-number" class="form-control" disabled />
                     </div>
                  </div>
                  <%-- state property --%>
                  <div id="behavioralprotocol-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="bp.state.id" />
                        </p>
                        <input type="text" id="property-bp-state-id" name="property-bp-state-id" class="form-control" disabled />
                     </div>
                     <%--
                        <div class="property">
                           <p>
                              <spring:message code="bp.state.type" />
                           </p>
                           <input type="text" id="property-bp-state-type" name="property-bp-state-type" class="form-control" disabled />
                        </div>
                        --%>
                     <div class="property">
                        <p>
                           <spring:message code="bp.state.name" />
                        </p>
                        <input type="text" id="property-bp-state-name" name="property-bp-state-name" data-parsley-state_exists_skip_selected="" data-parsley-trigger="focusout" required="required" class="form-control" />
                     </div>
                     <div class="property">
                        <p></p>
                        <button id="property-bp-state-update" type="button" class="btn btn-primary">
                           <spring:message code="common.update" />
                        </button>
                     </div>
                  </div>
                  <%-- transition property --%>
                  <div id="behavioralprotocol-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="bp.transition.id" />
                        </p>
                        <input type="text" id="property-bp-transition-id" name="property-bp-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="bp.transition.label" />
                        </p>
                        <input type="text" id="property-bp-transition-label" name="property-bp-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="bp.state.source" />
                        </p>
                        <input type="text" id="property-bp-transition-from" name="property-bp-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="bp.state.target" />
                        </p>
                        <input type="text" id="property-bp-transition-to" name="property-bp-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="bp.transition.type" />
                        </p>
                        <select id="property-bp-transition-type" name="property-bp-transition-type" class="form-control" tabindex="-1">
                           <option value="sendAction">
                              <spring:message code="bp.transition.type.send" />
                           </option>
                           <option value="receiveAction">
                              <spring:message code="bp.transition.type.receive" />
                           </option>
                           <option value="internalAction">
                              <spring:message code="bp.transition.type.internal" />
                           </option>
                        </select>
                     </div>
                     <div id="property-bp-transition-typedmessage-div">
                        <div class="property">
                           <p>
                              <spring:message code="bp.transition.typedmessage.name" />
                           </p>
                           <input type="text" id="property-bp-transition-typedmessage-name" name="property-bp-transition-typedmessage-name" data-parsley-trigger="focusout" required="required" class="form-control" />
                        </div>
                        <div class="property">
                           <p>
                              <spring:message code="bp.transition.typedmessage.content" />
                           </p>
                           <input type="text" id="property-bp-transition-typedmessage-content" name="property-bp-transition-typedmessage-content" data-parsley-trigger="focusout" required="required" class="form-control" />
                        </div>
                     </div>
                     <div class="property">
                        <p></p>
                        <button id="property-bp-transition-update" type="button" class="btn btn-primary">
                           <spring:message code="common.update" />
                        </button>
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
               <button id="locadBlueClient" class="btn btn-warning btn-sm">
                  <spring:message code="bp.load.blueclient" />
               </button>
               <button id="locadMoonService" class="btn btn-warning btn-sm">
                  <spring:message code="bp.load.moonservice" />
               </button>
               <%-- <button id="testMoonService" class="btn btn-warning btn-sm"> Test Moon Service protocol </button> --%>
            </div>
         </div>
      </div>
   </div>
</div>