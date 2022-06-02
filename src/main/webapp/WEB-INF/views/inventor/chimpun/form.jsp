<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<jstl:if test="${acme:anyOf(command, 'show, update, delete')}">
    	<acme:input-textbox code="authenticated.inventor.chimpun.form.label.code" path="code" readonly="true"/>	
		<acme:input-textbox code="authenticated.inventor.chimpun.form.label.creationMoment" path="creationMoment" readonly="true"/>	
	</jstl:if>
	<acme:input-textbox code="authenticated.inventor.chimpun.form.label.title" path="title"/>	
	<acme:input-textbox code="authenticated.inventor.chimpun.form.label.description" path="description"/>	
	<acme:input-moment code="authenticated.inventor.chimpun.form.label.startTime" path="startTime"/>
	<acme:input-moment code="authenticated.inventor.chimpun.form.label.endingTime" path="endingTime"/>
	<acme:input-money code="authenticated.inventor.chimpun.form.label.budget" path="budget"/>
	<acme:input-textbox code="authenticated.inventor.chimpun.form.label.optionalLink" path="optionalLink"/>
	<jstl:choose>	
			<jstl:when test="${noToolsError}">
				<acme:message code="authenticated.inventor.chimpun.form.button.noTools"></acme:message>
			</jstl:when>
			<jstl:otherwise>
			<acme:input-select code="inventor.chimpun.form.label.toolUpdate" path="itemId">
	   			<jstl:forEach items="${tools}" var="tool">
					<acme:input-option code="${tool.getName()}" value="${tool.getId()}"/>
				</jstl:forEach>
			</acme:input-select>
			</jstl:otherwise>
		</jstl:choose>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:button code="authenticated.inventor.chimpun.form.button.associatedTool" action="/inventor/item/show?id=${itemId}"/>
			<acme:submit code="authenticated.inventor.chimpun.form.button.update" action="/inventor/chimpun/update"/>
			<acme:submit code="authenticated.inventor.chimpun.form.button.delete" action="/inventor/chimpun/delete"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'create') && !noToolsError}">
			<acme:submit code="authenticated.inventor.chimpun.form.button.create" action="/inventor/chimpun/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>

