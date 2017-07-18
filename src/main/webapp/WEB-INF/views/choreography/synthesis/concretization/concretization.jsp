<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="concretization_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="choreography.coordination.logic.concretization" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <div id="coordinationdelegate_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>
            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label> <input type="checkbox" id="cd-physics" class="form-control js-switch" /> <spring:message code="choreography.editor.lock" /> <label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="choreography.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- participant property --%>
                  <div id="coordinationdelegate-participant-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.name" />
                        </p>
                        <input type="text" id="property-cd-participant-name" name="property-cd-participant-name" class="form-control" disabled />
                     </div>
                  </div>
                  
                  <%-- node property --%>
                  <div id="coordinationdelegate-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.id" />
                        </p>
                        <input type="text" id="property-cd-state-id" name="property-cd-state-id" class="form-control" disabled />
                     </div>
                     <%-- 
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.type" />
                        </p>
                        <input type="text" id="property-cd-state-type" name="property-cd-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.name" />
                        </p>
                        <input type="text" id="property-cd-state-name" name="property-cd-state-name" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant" />
                        </p>
                        <input type="text" id="property-cd-state-participant" name="property-cd-state-participant" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="coordinationdelegate-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.id" />
                        </p>
                        <input type="text" id="property-cd-transition-id" name="property-cd-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.type" />
                        </p>
                        <input type="text" id="property-cd-transition-type" name="property-cd-transition-type" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.label" />
                        </p>
                        <input type="text" id="property-cd-transition-label" name="property-cd-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-cd-transition-from" name="property-cd-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-cd-transition-to" name="property-cd-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.message.name" />
                        </p>
                        <input type="text" id="property-cd-transition-message" name="property-cd-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.sending.message" />
                        </p>
                        <input type="text" id="property-cd-transition-message-participant-sending" name="property-cd-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.receiving.message" />
                        </p>
                        <input type="text" id="property-cd-transition-message-participant-receiving" name="property-cd-transition-message" class="form-control" disabled />
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
            <%--
               <button id="locadCoordinationDelegates" class="btn btn-warning btn-sm">
                  <spring:message code="coordination.delegate.load" />
               </button>
             --%>
               <button id="generateCoordinationDelegates" class="btn btn-success btn-sm">
                  <spring:message code="choreography.coordination.logic.concretization" />
               </button>               
            </div>
         </div>
      </div>
   </div>
</div>
