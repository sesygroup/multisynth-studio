<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="projection_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="choreography.projection" />
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
            <div class="margin_top_20">
               <button id="generateProjections" class="btn btn-success btn-sm">
                  <spring:message code="choreography.projection" />
               </button>             
            </div>
         </div>
      </div>
   </div>
</div>