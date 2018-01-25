# 载入模块
import xlrd
import pandas
from numpy import *
from pandas import *
from datetime import datetime
from dateutil.parser import parse
import MySQLdb
#向已存在sheet的Excel中写入数据
from xlutils.copy import copy
from xlrd import open_workbook
from xlwt import easyxf
import os

# 打开一个workbook
workbook = xlrd.open_workbook('D:\\files\\file5.xls')
worksheets = workbook.sheet_names()
print('worksheets is %s' % worksheets)
# 定位到sheet1
worksheet1 = workbook.sheet_by_name(u'体检功能运营状况汇总表')
# 遍历sheet1中所有行row
num_rows = worksheet1.nrows



#Excel文件的路径
excel=r'D:\\files\\file5.xls'
#打开Excel文件
rb=open_workbook(excel,formatting_info=True)
#复制这个打开的文件
wb=copy(rb)
#从复制的Excel文件中，得到第二张sheet
sheet=wb.get_sheet(1)
#从复制的Excel文件中，得到第一张sheet
sheet1=wb.get_sheet(0)



# ******连接mysql数据库取数据******
# 建立数据库连接
conn = MySQLdb.connect(host="202.105.139.94", user="gboss_ro", passwd="123456", port=896, db="gboss", charset="utf8")
# 通过获取到的数据库连接conn下的cursor()方法来创建游标
cursor1 = conn.cursor()
cursor2 = conn.cursor()
cursor3 = conn.cursor()
cursor4 = conn.cursor()
cursor5 = conn.cursor()
cursor6 = conn.cursor()
cursor7 = conn.cursor()
cursor8 = conn.cursor()
# 解决乱码问题
cursor1.execute("SET NAMES utf8")
cursor2.execute("SET NAMES utf8")
cursor3.execute("SET NAMES utf8")
cursor4.execute("SET NAMES utf8")
cursor5.execute("SET NAMES utf8")
cursor6.execute("SET NAMES utf8")
cursor7.execute("SET NAMES utf8")
cursor8.execute("SET NAMES utf8")
# sql语句
sql1 = "SELECT * FROM t_hm_faultinfo WHERE starttime >= '2016-10-01 00:00:00' ORDER BY CALL_LETTER"
sql2 = "select * from t_hm_faultcode"
sql3 = "select * from t_hm_faulttype"
sql4 = "SELECT call_letter,vin,vehicle_type,equip_code FROM t_ba_sim WHERE subco_no =201 "
sql5 = "SELECT customer_id,phone FROM t_ba_linkman"
sql6 = "SELECT customer_id,customer_name  FROM t_ba_customer WHERE subco_no = 201 "
sql7 = "SELECT customer_id,call_letter FROM t_ba_unit WHERE subco_no =201"
sql8 = "SELECT * FROM t_temp_nodeinfo ORDER BY callLetter,gpsTime"
# 执行sql语句返回查询的表的行数
cursor1.execute(sql1)
cursor2.execute(sql2)
cursor3.execute(sql3)
cursor4.execute(sql4)
cursor5.execute(sql5)
cursor6.execute(sql6)
cursor7.execute(sql7)
cursor8.execute(sql8)
# 使用fetchall函数，将结果集（多维元组）存入rows里面
rows1 = cursor1.fetchall()
rows2 = cursor2.fetchall()
rows3 = cursor3.fetchall()
rows4 = cursor4.fetchall()
rows5 = cursor5.fetchall()
rows6 = cursor6.fetchall()
rows7 = cursor7.fetchall()
rows8 = cursor8.fetchall()
# 定义一个标记call_letter
global flag,flag2
flag = ''
#定义一个标记行
global count
count = 2
#将已写入数据的行进行标记，避免重复写入
flag2 = 0

#创建用于存放故障码的list
global arr_tbox,arr_apm,arr_ems,arr_immo,arr_icm,arr_abs_esp,arr_tpms,arr_tcu,arr_bcm,arr_eps,arr_peps,arr_srs
arr_tbox=[]
arr_apm=[]
arr_ems=[]
arr_immo=[]
arr_icm=[]
arr_abs_esp=[]
arr_tpms=[]
arr_tcu=[]
arr_bcm=[]
arr_eps=[]
arr_peps=[]
arr_srs=[]

#创建一个存储所有故障码的list
global array_tbox,array_apm,array_ems,array_immo,array_icm,array_abs_esp,array_tpms,array_tcu,array_bcm,array_eps,array_peps,array_srs
array_tbox=[]
array_apm=[]
array_ems=[]
array_immo=[]
array_icm=[]
array_abs_esp=[]
array_tpms=[]
array_tcu=[]
array_bcm=[]
array_eps=[]
array_peps=[]
array_srs=[]
#定义故障个数
global tbox,apm,ems,immo,icm,abs_esp,tpms,tcu,bcm,eps,peps,srs
tbox=0
apm=0
ems=0
immo=0
icm=0
abs_esp=0
tpms=0
tcu=0
bcm=0
eps=0
peps=0
srs=0

#创建用于存放故障码的list
global callletter_tbox,callletter_apm,callletter_ems,callletter_immo,callletter_icm,callletter_abs_esp,callletter_tpms,callletter_tcu,callletter_bcm,callletter_eps,callletter_peps,callletter_srs
callletter_tbox=[]
callletter_apm=[]
callletter_ems=[]
callletter_immo=[]
callletter_icm=[]
callletter_abs_esp=[]
callletter_tpms=[]
callletter_tcu=[]
callletter_bcm=[]
callletter_eps=[]
callletter_peps=[]
callletter_srs=[]

#将故障灯字段分成两组存到list中
arr=[]
#标记GPS全为零的字段
flag_endtime=0
#定义GPS开始和结束时间
start_gpstime=''
end_gpstime=''
#用于标记一个呼号最后一个含1的字段
lastnodeInfo=''
#一个用户可能有几个电话，创建一个list存储
arr_phone=[]

#TBOX故障信息数组
tbox_time=[]
tbox_faultinfo=[]
tbox_faulname=[]

#EMS故障信息数组
ems_time=[]
ems_faultinfo=[]
ems_faulname=[]

#TCU故障信息数组
tcu_time=[]
tcu_faultinfo=[]
tcu_faulname=[]

#ABS_ESP故障信息数组
abs_esp_time=[]
abs_esp_faultinfo=[]
abs_esp_faulname=[]

#TPMS故障信息数组
tpms_time=[]
tpms_faultinfo=[]
tpms_faulname=[]

#BCM故障信息数组
bcm_time=[]
bcm_faultinfo=[]
bcm_faulname=[]

#IMMO故障信息数组
immo_time=[]
immo_faultinfo=[]
immo_faulname=[]

#APM故障信息数组
apm_time=[]
apm_faultinfo=[]
apm_faulname=[]

#ICM故障信息数组
icm_time=[]
icm_faultinfo=[]
icm_faulname=[]

#EPS故障信息数组
eps_time=[]
eps_faultinfo=[]
eps_faulname=[]

#PEPS故障信息数组
peps_time=[]
peps_faultinfo=[]
peps_faulname=[]

#SRS故障信息数组
srs_time=[]
srs_faultinfo=[]
srs_faulname=[]


# 依次遍历结果集，发现每个元素，就是表中的一条记录，用一个元组来显示
for row1 in rows1:
    if flag != row1[3]:
        flag=row1[3]
        flag2=0
        count=count+1
        sheet.write(count, 0,count-2 )
        sheet.write(count, 6, row1[3])
        if count>3:#每一个字段换行输出
            sheet.write(count-1, 11, '\n'.join(tbox_time))  #TBOX
            sheet.write(count-1, 12, '\n'.join(tbox_faultinfo))
            sheet.write(count-1, 13, '\n'.join(tbox_faulname))

            sheet.write(count-1, 14, '\n'.join(ems_time))  # EMS
            sheet.write(count-1, 15, '\n'.join(ems_faultinfo))
            sheet.write(count-1, 16, '\n'.join(ems_faulname))

            sheet.write(count-1, 17, '\n'.join(tcu_time))  # TCU
            sheet.write(count-1, 18, '\n'.join(tcu_faultinfo))
            sheet.write(count-1, 19, '\n'.join(tcu_faulname))

            sheet.write(count-1, 20, '\n'.join(abs_esp_time))  # ABS_ESP
            sheet.write(count-1, 21, '\n'.join(abs_esp_faultinfo))
            sheet.write(count-1, 22, '\n'.join(abs_esp_faulname))

            sheet.write(count-1, 23, '\n'.join(tpms_time))  # TPMS
            sheet.write(count-1, 24, '\n'.join(tpms_faultinfo))
            sheet.write(count-1, 25, '\n'.join(tpms_faulname))

            sheet.write(count-1, 26, '\n'.join(bcm_time))  # BCM
            sheet.write(count-1, 27, '\n'.join(bcm_faultinfo))
            sheet.write(count-1, 28, '\n'.join(bcm_faulname))

            sheet.write(count-1, 29, '\n'.join(immo_time))  # IMMO
            sheet.write(count-1, 30, '\n'.join(immo_faultinfo))
            sheet.write(count-1, 31, '\n'.join(immo_faulname))

            sheet.write(count-1, 32, '\n'.join(apm_time))  # APM
            sheet.write(count-1, 33, '\n'.join(apm_faultinfo))
            sheet.write(count-1, 34, '\n'.join(apm_faulname))

            sheet.write(count-1, 35, '\n'.join(icm_time))  # ICM
            sheet.write(count-1, 36, '\n'.join(icm_faultinfo))
            sheet.write(count-1, 37, '\n'.join(icm_faulname))

            sheet.write(count-1, 38, '\n'.join(eps_time))  # EPS
            sheet.write(count-1, 39, '\n'.join(eps_faultinfo))
            sheet.write(count-1, 40, '\n'.join(eps_faulname))

            sheet.write(count-1, 41, '\n'.join(peps_time))  # PEPS
            sheet.write(count-1, 42, '\n'.join(peps_faultinfo))
            sheet.write(count-1, 43, '\n'.join(peps_faulname))

            sheet.write(count-1, 44, '\n'.join(srs_time))  # SRS
            sheet.write(count-1, 45, '\n'.join(srs_faultinfo))
            sheet.write(count-1, 46, '\n'.join(srs_faulname))
        # TBOX故障信息数组
        tbox_time = []
        tbox_faultinfo = []
        tbox_faulname = []

        # EMS故障信息数组
        ems_time = []
        ems_faultinfo = []
        ems_faulname = []

        # TCU故障信息数组
        tcu_time = []
        tcu_faultinfo = []
        tcu_faulname = []

        # ABS_ESP故障信息数组
        abs_esp_time = []
        abs_esp_faultinfo = []
        abs_esp_faulname = []

        # TPMS故障信息数组
        tpms_time = []
        tpms_faultinfo = []
        tpms_faulname = []

        # BCM故障信息数组
        bcm_time = []
        bcm_faultinfo = []
        bcm_faulname = []

        # IMMO故障信息数组
        immo_time = []
        immo_faultinfo = []
        immo_faulname = []

        # APM故障信息数组
        apm_time = []
        apm_faultinfo = []
        apm_faulname = []

        # ICM故障信息数组
        icm_time = []
        icm_faultinfo = []
        icm_faulname = []

        # EPS故障信息数组
        eps_time = []
        eps_faultinfo = []
        eps_faulname = []

        # PEPS故障信息数组
        peps_time = []
        peps_faultinfo = []
        peps_faulname = []

        # SRS故障信息数组
        srs_time = []
        srs_faultinfo = []
        srs_faulname = []
    else:
        flag2=1


    for row2 in rows2:  # 在t_hm_faultcode
        if row1[2] == row2[1]:
            for row3 in rows3:  # 在t_hm_faulttype
                if row1[1] == row3[0]:
                   if row3[1]=='TBOX':
                       if row1[2] not in arr_tbox:#将不同的TBOX故障码加入list
                            arr_tbox.append(row1[2])
                       if row1[3] not in callletter_tbox:
                           callletter_tbox.append(row1[3])
                       array_tbox.append(row1[2])
                       tbox=tbox+1
                       tbox_time.append(str(row1[0]))
                       tbox_faultinfo.append(str(row1[1])+"/"+"["+str(row1[2])+"]")
                       tbox_faulname.append(row2[2])

                   elif row3[1]=='EMS':
                       if row1[2] not in arr_ems:#将不同的EMS故障码加入list
                            arr_ems.append(row1[2])
                       if row1[3] not in callletter_ems:
                           callletter_ems.append(row1[3])
                       array_ems.append(row1[2])
                       ems=ems+1
                       ems_time.append(str(row1[0]))
                       ems_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       ems_faulname.append(row2[2])

                   elif row3[1] == 'TCU':
                       if row1[2] not in arr_tcu:#将不同的TCU故障码加入list
                            arr_tcu.append(row1[2])
                       if row1[3] not in callletter_tcu:
                           callletter_tcu.append(row1[3])
                       array_tcu.append(row1[2])
                       tcu=tcu+1
                       tcu_time.append(str(row1[0]))
                       tcu_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       tcu_faulname.append(row2[2])

                   elif row3[1] == 'ABS' or row3[1] == 'ESP':
                       if row1[2] not in arr_abs_esp:#将不同的ABS-ESP故障码加入list
                            arr_abs_esp.append(row1[2])
                       if row1[3] not in callletter_abs_esp:
                           callletter_abs_esp.append(row1[3])
                       array_abs_esp.append(row1[2])
                       abs_esp=abs_esp+1
                       abs_esp_time.append(str(row1[0]))
                       abs_esp_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       abs_esp_faulname.append(row2[2])

                   elif row3[1] == 'TPMS':
                       if row1[2] not in arr_tpms:#将不同的TPMS故障码加入list
                            arr_tpms.append(row1[2])
                       if row1[3] not in callletter_tpms:
                           callletter_tpms.append(row1[3])
                       array_tpms.append(row1[2])
                       tpms=tpms+1
                       tpms_time.append(str(row1[0]))
                       tpms_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       tpms_faulname.append(row2[2])

                   elif row3[1] == 'BCM':
                       if row1[2] not in arr_bcm:#将不同的BCM故障码加入list
                            arr_bcm.append(row1[2])
                       if row1[3] not in callletter_bcm:
                           callletter_bcm.append(row1[3])
                       array_bcm.append(row1[2])
                       bcm=bcm+1
                       bcm_time.append(str(row1[0]))
                       bcm_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       bcm_faulname.append(row2[2])

                   elif row3[1] == 'IMMO':
                       if row1[2] not in arr_immo:#将不同的IMMO故障码加入list
                            arr_immo.append(row1[2])
                       if row1[3] not in callletter_immo:
                           callletter_immo.append(row1[3])
                       array_immo.append(row1[2])
                       immo=immo+1
                       immo_time.append(str(row1[0]))
                       immo_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       immo_faulname.append(row2[2])

                   elif row3[1] == 'APM':
                       if row1[2] not in arr_apm:#将不同的APM故障码加入list
                            arr_apm.append(row1[2])
                       if row1[3] not in callletter_apm:
                           callletter_apm.append(row1[3])
                       array_apm.append(row1[2])
                       apm=apm+1
                       apm_time.append(str(row1[0]))
                       apm_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       apm_faulname.append(row2[2])

                   elif row3[1] == 'ICM':
                       if row1[2] not in arr_icm:#将不同的ICM故障码加入list
                            arr_icm.append(row1[2])
                       if row1[3] not in callletter_icm:
                           callletter_icm.append(row1[3])
                       array_icm.append(row1[2])
                       icm=icm+1
                       icm_time.append(str(row1[0]))
                       icm_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       icm_faulname.append(row2[2])

                   elif row3[1] == 'EPS':
                       if row1[2] not in arr_eps:  # 将不同的EPS故障码加入list
                           arr_eps.append(row1[2])
                       if row1[3] not in callletter_eps:
                           callletter_eps.append(row1[3])
                       array_eps.append(row1[2])
                       eps = eps + 1
                       eps_time.append(str(row1[0]))
                       eps_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       eps_faulname.append(row2[2])

                   elif row3[1] == 'PEPS':
                       if row1[2] not in arr_peps:  # 将不同的PEPS故障码加入list
                           arr_peps.append(row1[2])
                       if row1[3] not in callletter_peps:
                           callletter_peps.append(row1[3])
                       array_peps.append(row1[2])
                       peps = peps + 1
                       peps_time.append(str(row1[0]))
                       peps_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       peps_faulname.append(row2[2])

                   elif row3[1] == 'SRS':
                       if row1[2] not in arr_srs:  # 将不同的SRS故障码加入list
                           arr_srs.append(row1[2])
                       if row1[3] not in callletter_srs:
                           callletter_srs.append(row1[3])
                       array_srs.append(row1[2])
                       srs = srs + 1
                       srs_time.append(str(row1[0]))
                       srs_faultinfo.append(str(row1[1]) + "/" + "[" + str(row1[2]) + "]")
                       srs_faulname.append(row2[2])



    if flag2==0:#同一个callletter
        for row4 in rows4:  # 在t_hm_sim
            if row1[3] == row4[0]:
                sheet.write(count, 5, row4[1])
                sheet.write(count, 1, row4[2])
                sheet.write(count, 2, row4[3])
                break
        arr_phone=[]
        for row7 in rows7:
            if row1[3] == row7[1]:
                for row5 in rows5:#在t_ba_linkman中查找phone
                    if row5[0] == row7[0]:
                        if row5[1] not in arr_phone:#避免电话号码重复
                            arr_phone.append(row5[1])

                        # sheet.write(count,4,row5[1])
                        # break
                str_phone = ','.join(arr_phone)
                sheet.write(count, 4, str_phone)
                # print('电话号码：'+str_phone)
                for row6 in rows6:#在t_ba_customer中查找customer_name
                    if row6[0] == row7[0]:
                        sheet.write(count, 3, row6[1])
                        break

        nodeLostInfo = []
        nodeFaultInfo = []
        start_gpstime = ''
        end_gpstime = ''
        flag_endtime = 0
        lastnodeInfo = ''
        for row8 in rows8:
            if row1[3] == row8[1]:
                arr = row8[3].split("}")
                if '1' in arr[0]:  # 判断nodeFaultInfo中是否存在1
                    flag_endtime = 1
                    lastnodeInfo = row8[2]
                    if 'abs:1' in arr[0]:  # 对每一个字段进行判断
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'abs:1' not in nodeLostInfo:
                            nodeLostInfo.append('abs:1')
                    if 'esp:1' in arr[0]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'esp:1' not in nodeLostInfo:
                            nodeLostInfo.append('esp:1')
                    if 'ems:1' in arr[0]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'ems:1' not in nodeLostInfo:
                            nodeLostInfo.append('ems:1')
                    if 'peps:1' in arr[0]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'peps:1' not in nodeLostInfo:
                            nodeLostInfo.append('peps:1')
                    if 'tcu:1' in arr[0]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'tcu:1' not in nodeLostInfo:
                            nodeLostInfo.append('tcu:1')
                    if 'bcm:1' in arr[0]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'bcm:1' not in nodeLostInfo:
                            nodeLostInfo.append('bcm:1')
                    if 'icm:1' in arr[0]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'icm:1' not in nodeLostInfo:
                            nodeLostInfo.append('icm:1')
                if '1' in arr[1]:  # 判断nodeFaultInfo中是否存在1
                    flag_endtime = 1
                    lastnodeInfo = row8[2]
                    if 'ebd:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'ebd:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('ebd:1')
                    if 'abs:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'abs:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('abs:1')
                    if 'esp:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'esp:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('esp:1')
                    if 'svs:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'svs:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('svs:1')
                    if 'mil:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'mil:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('mil:1')
                    if 'tcu:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'tcu:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('tcu:1')
                    if 'peps:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'peps:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('peps:1')
                    if 'tbox:1' in arr[1]:
                        if len(nodeLostInfo) == 0 and len(nodeFaultInfo) == 0:  # 如果两个list为空，此时为starttime
                            start_gpstime = row8[2]
                            # print(row8[2])
                        if 'tbox:1' not in nodeFaultInfo:
                            nodeFaultInfo.append('tbox:1')
                if '1' not in arr:
                    if flag_endtime != 0:
                        flag_endtime = 0
                        end_gpstime = row8[2]
                    else:
                        continue
            else:
                if flag_endtime == 1:
                    end_gpstime = lastnodeInfo
                    break
        #将故障灯信息写入excel
        if len(nodeLostInfo)==0:
            sheet.write(count, 7, 0)
        else:
            sheet.write(count, 7, ','.join(nodeLostInfo))

        if len(nodeFaultInfo)==0:
            sheet.write(count, 8, 0)
        else:
            sheet.write(count, 8, ','.join(nodeFaultInfo))

        sheet.write(count, 9, start_gpstime)
        sheet.write(count, 10, end_gpstime)
        # print(count-2,row1[3], start_gpstime,nodeLostInfo, nodeFaultInfo,end_gpstime)

#输出最后一行的故障信息，每一个字段换行输出
sheet.write(count, 11, '\n'.join(tbox_time))#TBOX
sheet.write(count, 12, '\n'.join(tbox_faultinfo))
sheet.write(count, 13, '\n'.join(tbox_faulname))

sheet.write(count, 14, '\n'.join(ems_time))  # EMS
sheet.write(count, 15, '\n'.join(ems_faultinfo))
sheet.write(count, 16, '\n'.join(ems_faulname))

sheet.write(count, 17, '\n'.join(tcu_time))  # TCU
sheet.write(count, 18, '\n'.join(tcu_faultinfo))
sheet.write(count, 19, '\n'.join(tcu_faulname))

sheet.write(count, 20, '\n'.join(abs_esp_time))  # ABS_ESP
sheet.write(count, 21, '\n'.join(abs_esp_faultinfo))
sheet.write(count, 22, '\n'.join(abs_esp_faulname))

sheet.write(count, 23, '\n'.join(tpms_time))  # TPMS
sheet.write(count, 24, '\n'.join(tpms_faultinfo))
sheet.write(count, 25, '\n'.join(tpms_faulname))

sheet.write(count, 26, '\n'.join(bcm_time))  # BCM
sheet.write(count, 27, '\n'.join(bcm_faultinfo))
sheet.write(count, 28, '\n'.join(bcm_faulname))

sheet.write(count, 29, '\n'.join(immo_time))  # IMMO
sheet.write(count, 30, '\n'.join(immo_faultinfo))
sheet.write(count, 31, '\n'.join(immo_faulname))

sheet.write(count, 32, '\n'.join(apm_time))  # APM
sheet.write(count, 33, '\n'.join(apm_faultinfo))
sheet.write(count, 34, '\n'.join(apm_faulname))

sheet.write(count, 35, '\n'.join(icm_time))  # ICM
sheet.write(count, 36, '\n'.join(icm_faultinfo))
sheet.write(count, 37, '\n'.join(icm_faulname))

sheet.write(count, 38, '\n'.join(eps_time))  # EPS
sheet.write(count, 39, '\n'.join(eps_faultinfo))
sheet.write(count, 40, '\n'.join(eps_faulname))

sheet.write(count, 41, '\n'.join(peps_time))  # PEPS
sheet.write(count, 42, '\n'.join(peps_faultinfo))
sheet.write(count, 43, '\n'.join(peps_faulname))

sheet.write(count, 44, '\n'.join(srs_time))  # SRS
sheet.write(count, 45, '\n'.join(srs_faultinfo))
sheet.write(count, 46, '\n'.join(srs_faulname))


str_total='车辆总数：'+str(count-2)
sheet1.write(2,4,str_total)
#ICM故障
sheet1.write(4,2,len(arr_icm))
sheet1.write(4,3,len(array_icm))
sheet1.write(4,4,len(callletter_icm))
sheet1.write(4,5,round(len(callletter_icm)/(count-2)*100,2))
#APM故障
sheet1.write(5,2,len(arr_apm))
sheet1.write(5,3,len(array_apm))
sheet1.write(5,4,len(callletter_apm))
sheet1.write(5,5,round(len(callletter_apm)/(count-2)*100,2))
#EMS故障
sheet1.write(6,2,len(arr_ems))
sheet1.write(6,3,len(array_ems))
sheet1.write(6,4,len(callletter_ems))
sheet1.write(6,5,round(len(callletter_ems)/(count-2)*100,2))
#IMMO故障
sheet1.write(7,2,len(arr_immo))
sheet1.write(7,3,len(array_immo))
sheet1.write(7,4,len(callletter_immo))
sheet1.write(7,5,round(len(callletter_immo)/(count-2)*100,2))
#TBOX故障
sheet1.write(8,2,len(arr_immo))
sheet1.write(8,3,len(array_immo))
sheet1.write(8,4,len(callletter_immo))
sheet1.write(8,5,round(len(callletter_immo)/(count-2)*100,2))
#ABS_ESP故障
sheet1.write(9,2,len(arr_abs_esp))
sheet1.write(9,3,len(array_abs_esp))
sheet1.write(9,4,len(callletter_abs_esp))
sheet1.write(9,5,round(len(callletter_abs_esp)/(count-2)*100,2))
#TPMS故障
sheet1.write(10,2,len(arr_tpms))
sheet1.write(10,3,len(array_tpms))
sheet1.write(10,4,len(callletter_tpms))
sheet1.write(10,5,round(len(callletter_tpms)/(count-2)*100,2))
#BCM故障
sheet1.write(11,2,len(arr_bcm))
sheet1.write(11,3,len(array_bcm))
sheet1.write(11,4,len(callletter_bcm))
sheet1.write(11,5,round(len(callletter_bcm)/(count-2)*100,2))
#TCU故障
sheet1.write(12,2,len(arr_tcu))
sheet1.write(12,3,len(array_tcu))
sheet1.write(12,4,len(callletter_tcu))
sheet1.write(12,5,round(len(callletter_tcu)/(count-2)*100,))
#PEPS故障
sheet1.write(13,2,len(arr_peps))
sheet1.write(13,3,len(array_peps))
sheet1.write(13,4,len(callletter_peps))
sheet1.write(13,5,round(len(callletter_peps)/(count-2)*100,2))
#EPS故障
sheet1.write(14,2,len(arr_eps))
sheet1.write(14,3,len(array_eps))
sheet1.write(14,4,len(callletter_eps))
sheet1.write(14,5,round(len(callletter_eps)/(count-2)*100,2))
#SRS故障
sheet1.write(15,2,len(arr_srs))
sheet1.write(15,3,len(array_srs))
sheet1.write(15,4,len(callletter_srs))
sheet1.write(15,5,round(len(callletter_srs)/(count-2)*100,2))
#计算故障总和
sum1=len(arr_icm)+len(arr_apm)+len(arr_ems)+len(arr_immo)+len(arr_tbox)+len(arr_abs_esp)+len(arr_tpms)+len(arr_bcm)+len(arr_tcu)+len(arr_peps)+len(arr_eps)+len(arr_srs)
sum2=len(array_icm)+len(array_apm)+len(array_ems)+len(array_immo)+len(array_tbox)+len(array_abs_esp)+len(array_tpms)+len(array_bcm)+len(array_tcu)+len(array_peps)+len(array_eps)+len(array_srs)
sum3=len(callletter_icm)+len(callletter_apm)+len(callletter_ems)+len(callletter_immo)+len(callletter_tbox)+len(callletter_abs_esp)+len(callletter_tpms)+len(callletter_bcm)+len(callletter_tcu)+len(callletter_peps)+len(callletter_eps)+len(callletter_srs)
sheet1.write(17,2,sum1)
sheet1.write(17,3,sum2)
sheet1.write(17,4,sum3)
sheet1.write(17,5,100)

#对每一种故障计数
count_tbox=0
#标记faultname
flag_faultname=''
#将list每个数输出
# print("TBOX")
arr_tbox=sorted(arr_tbox)
array_tbox=sorted(array_tbox)

#定义输出excel的行
global count_excel
count_excel=1
sheet1.write(count_excel,7,"TBOX")
for row_arr in arr_tbox:
    count_tbox = 0
    count_excel=count_excel+1
    for row_array in array_tbox:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel,8,row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr,flag_faultname,count_tbox)
# print("APM")
count_excel=count_excel+1
sheet1.write(count_excel,7,"APM")
arr_apm=sorted(arr_apm)
array_apm=sorted(array_apm)
for row_arr in arr_apm:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_apm:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)
# print("EMS")
count_excel=count_excel+1
sheet1.write(count_excel,7,"EMS")
arr_ems=sorted(arr_ems)
array_ems=sorted(array_ems)
for row_arr in arr_ems:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_ems:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)
# print("IMMO")
count_excel=count_excel+1
sheet1.write(count_excel,7,"IMMO")
arr_immo=sorted(arr_immo)
array_immo=sorted(array_immo)
for row_arr in arr_immo:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_immo:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)
# print("ICM")
count_excel=count_excel+1
sheet1.write(count_excel,7,"ICM")
arr_icm=sorted(arr_icm)
array_icm=sorted(array_icm)
for row_arr in arr_icm:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_icm:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)
# print("ABS_ESP")
count_excel=count_excel+1
sheet1.write(count_excel,7,"ABS_ESP")
arr_abs_esp=sorted(arr_abs_esp)
array_abs_esp=sorted(array_abs_esp)
for row_arr in arr_abs_esp:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_abs_esp:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)
# print("TPMS")
count_excel=count_excel+1
sheet1.write(count_excel,7,"TPMS")
arr_tpms=sorted(arr_tpms)
array_tpms=sorted(array_tpms)
for row_arr in arr_tpms:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_tpms:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)
# print("TCU")
count_excel=count_excel+1
sheet1.write(count_excel,7,"TCU")
arr_tcu=sorted(arr_tcu)
array_tcu=sorted(array_tcu)
for row_arr in arr_tcu:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_tcu:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)

# print("BCM")
count_excel=count_excel+1
sheet1.write(count_excel,7,"BCM")
arr_bcm=sorted(arr_bcm)
array_bcm=sorted(array_bcm)
for row_arr in arr_bcm:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_bcm:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)

# print("EPS")
count_excel=count_excel+1
sheet1.write(count_excel,7,"EPS")
arr_eps=sorted(arr_eps)
array_eps=sorted(array_eps)
for row_arr in arr_eps:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_eps:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)

# print("PEPS")
count_excel=count_excel+1
sheet1.write(count_excel,7,"PEPS")
arr_peps=sorted(arr_peps)
array_peps=sorted(array_peps)
for row_arr in arr_peps:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_peps:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)

# print("SRS")
count_excel=count_excel+1
sheet1.write(count_excel,7,"SRS")
arr_srs=sorted(arr_srs)
array_srs=sorted(array_srs)
for row_arr in arr_srs:
    count_tbox = 0
    count_excel = count_excel + 1
    for row_array in array_srs:
        if row_arr==row_array:
            count_tbox = count_tbox+1
    for row2 in rows2:  # 在t_hm_faultcode
        if row_arr == row2[1]:
            flag_faultname=row2[2]
            break
    sheet1.write(count_excel, 8, row2[1])
    sheet1.write(count_excel, 9, flag_faultname)
    sheet1.write(count_excel, 10, count_tbox)
    # print(row_arr, flag_faultname, count_tbox)



# 删掉原先存在的Excel
os.remove(excel)
# 保存现有的workbook，得到新的Excel
wb.save(excel)
# 关闭游标
cursor1.close()
cursor2.close()
cursor3.close()
cursor4.close()
cursor5.close()
cursor6.close()
cursor7.close()
cursor8.close()
# 关闭数据库连接
conn.close()
