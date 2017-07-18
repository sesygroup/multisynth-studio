<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="coordination_logic_extraction_javascript.jsp"%>

<div class="row">
   <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="x_panel">
         <div class="x_title">
            <h2>
               <spring:message code="choreography.coordination.logic.extraction" />
            </h2>
            <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
            </ul>
            <div class="clearfix"></div>
         </div>
         <div class="x_content">
            <div id="coordinationlogicextraction_vis_container" class="col-md-8 col-sm-8 col-xs-12 vis_container"></div>
            <div class="col-md-4 col-sm-4 col-xs-12">
               <div class="">
                  <label> <input type="checkbox" id="coordinationlogicextraction-physics" class="form-control js-switch" /> <spring:message code="choreography.editor.lock" /> <label>
               </div>
               <div class="margin_top_20">
                  <div class="x_title">
                     <h2>
                        <spring:message code="choreography.properties" />
                     </h2>
                     <div class="clearfix"></div>
                  </div>
                  <%-- participant property --%>
                  <div id="coordinationlogicextraction-participant-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.name" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-participant-name" name="property-coordinationlogicextraction-participant-name" class="form-control" disabled />
                     </div>
                  </div>
                  
                  <%-- node property --%>
                  <div id="coordinationlogicextraction-node-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.id" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-state-id" name="property-coordinationlogicextraction-state-id" class="form-control" disabled />
                     </div>
                     <%-- 
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.type" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-state-type" name="property-coordinationlogicextraction-state-type" class="form-control" disabled />
                     </div>
                     --%>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.name" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-state-name" name="property-coordinationlogicextraction-state-name" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-state-participant" name="property-coordinationlogicextraction-state-participant" class="form-control" disabled />
                     </div>
                  </div>

                  <%-- transition property --%>
                  <div id="coordinationlogicextraction-transition-properties" class="col-md-12 col-sm-12 col-xs-12" style="display: none">
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.id" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-id" name="property-coordinationlogicextraction-transition-id" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.type" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-type" name="property-coordinationlogicextraction-transition-type" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.transition.label" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-label" name="property-coordinationlogicextraction-transition-label" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-from" name="property-coordinationlogicextraction-transition-from" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.state.source" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-to" name="property-coordinationlogicextraction-transition-to" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.message.name" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-message" name="property-coordinationlogicextraction-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.sending.message" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-message-participant-sending" name="property-coordinationlogicextraction-transition-message" class="form-control" disabled />
                     </div>
                     <div class="property">
                        <p>
                           <spring:message code="choreography.participant.receiving.message" />
                        </p>
                        <input type="text" id="property-coordinationlogicextraction-transition-message-participant-receiving" name="property-coordinationlogicextraction-transition-message" class="form-control" disabled />
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin_top_20">
            <%--
               <button id="locadAbstractCoordinationLogic" class="btn btn-warning btn-sm">
                  <spring:message code="abstract.coordination.logic.load" />
               </button>
            --%>
               <button id="extractCoordinationLogic" class="btn btn-success btn-sm">
                  <spring:message code="choreography.coordination.logic.extraction" />
               </button>               
            </div>
         </div>
      </div>
   </div>
</div>