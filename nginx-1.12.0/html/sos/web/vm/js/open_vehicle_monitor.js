/**
 * 添加到监控列表
 */
function addVehicleMonitor(vehicle) {
 if (!vehicle.numberPlate || !vehicle.callLetter) {
  return;
 }
 /* 窗口已存在，直接调用方法 */
 if (addVehicleMonitor.win && !addVehicleMonitor.win.closed) {
  addVehicleMonitor.win.addToWatchList({
   list : [ vehicle ]
  });
 } else {

  /* 第一次打开，不能调用方法，只能以参数传过去 */
  addVehicleMonitor.win = window.open("vehicle_monitor.html?plate_no=" + escape(vehicle.numberPlate) + "&call_letter=" + vehicle.callLetter
    + "&unit_id=" + vehicle.unitId + "&vehicle_id=" + vehicle.vehicleId, "VMS");
 }
 addVehicleMonitor.win.focus();
}