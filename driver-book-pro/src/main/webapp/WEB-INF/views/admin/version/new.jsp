<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>

<div class="pageContent">
	<form method="post" action="<%= basePath %>/admin/versions/new" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this, dialogAjaxDone);" style="height: 98% position : relative">
		<div class="pageFormContent" layoutH="58">
			<p>
				<label>
					APK文件：
				</label>
				<input name="apkFile" class="required" type="file"/>
			</p>
			<p>
				<label>
					更新说明：
				</label>
				<textarea name="caption" class="required" rows="5" cols="37"></textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
