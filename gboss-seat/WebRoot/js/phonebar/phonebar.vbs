Function CreateVBArr(p1,p2,p3,p4,p5)
	Dim arr(4)
	arr(0) = p1
	arr(1) = p2
	arr(2) = p3
	arr(3) = p4
	arr(4) = p5
	CreateVBArr = arr
End Function

//当与ICM连接成功后触发这个事件
Sub PhoneBar20_OnHello(param1,param2,param3)
  OnHello
End Sub

//状态改变
Sub PhoneBar20_OnStatus(curStatus)
 Call OnStatus(curStatus)
End Sub

//当有客户分配过来，或者座席被呼叫、被邀请、被转接时触发这个事件
Sub PhoneBar20_OninvInvitingX(Locator, invOID, cnvOID, usrOID, CustomerOID, CustomerContext, CustomerContact, Flags,Dates)
 Call OninvInvitingX(Locator, invOID, cnvOID, usrOID, CustomerOID, CustomerContext, CustomerContact, Flags,Dates)
End Sub

//呼叫、邀请、转接成功时触发这个事件
Sub PhoneBar20_OninvSucceedX(Locator,invOID,cnvOID,usrOID,CustomerOID,CustomerContext,CustomerContact,Flags,Times)
  Call OninvSucceedX(Locator,invOID,cnvOID,usrOID,CustomerOID,CustomerContext,CustomerContact,Flags,Times)
End Sub

//离开会话
Sub PhoneBar20_OncnvLeaveX(Locator, cnvOID,bMonitor,Times )
  Call OncnvLeaveX(Locator, cnvOID,bMonitor,Times )
End Sub

//邀请结束
Sub PhoneBar20_OnInvitationCompleted(OID,Failed,iReason,Reason)
  Call OnInvitationCompleted(OID,Failed,iReason,Reason)
End Sub

//监听、插话、代答、主动接听(正在振铃)
Sub PhoneBar20_OnswiSwitchingX(Locator, swiOID, cnvOID, CustomerContext, Times)
 Call OnswiSwitchingX(Locator, swiOID, cnvOID, CustomerContext, Times)
End Sub
//监听、插话、代答、主动接听(取消)
Sub PhoneBar20_OnswiCancelledX(Locator, swiOID, cnvOID, CustomerContext, iReason, Reason, Times)
 Call OnswiCancelledX(Locator, swiOID, cnvOID, CustomerContext, iReason, Reason, Times)
End Sub
//监听、插话、代答、主动接听(接通)
Sub PhoneBar20_OnswiSucceedX(Locator, swiOID, cnvOID, CustomerContext,Times)
 Call OnswiSucceedX(Locator, swiOID, cnvOID, CustomerContext,Times)
End Sub
//取消邀请
Sub PhoneBar20_OninvCancelledX(Locator, invOID, cnvOID, usrOID, CustomerOID, CustomerContext, Flags, iReason, Reason, Time)
  Call OninvCancelledX(Locator, invOID, cnvOID, usrOID, CustomerOID, CustomerContext, Flags, iReason, Reason, Time)
End Sub
//IVR回调
Sub PhoneBar20_OnExecuteResult(Responser, AppID, ErrCode, Result, AppData)
  Call OnExecuteResult(Responser, AppID, ErrCode, Result, AppData)
End Sub
//动作完成
Sub PhoneBar20_OnOperationCompleted(OID,Result,iReason,Reason,AppData )
  Call OnOperationCompleted(OID,Result,iReason,Reason,AppData)
End Sub

Dim icmTime
Function getIcmTime(deltaTime)
 icmTime = date + time + deltaTime
End Function