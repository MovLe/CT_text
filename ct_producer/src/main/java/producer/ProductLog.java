package producer;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ProductLog
 * @MethodDesc: 产生通信数据
 * @Author Movle
 * @Date 1/28/20 3:18 下午
 * @Version 1.0
 * @Email movle_xjk@foxmail.com
 **/


public class ProductLog {

    private String startTime="2020-01-01";
    private String endTime="2020-05-01";

    private List<String> phoneList = new ArrayList<String>();

    private Map<String,String> phoneNameMap= new HashMap<String, String>();

    public void initPhone(){

        phoneList.add("15369468720");
        phoneList.add("19920860202");
        phoneList.add("18411925860");
        phoneList.add("14473548449");
        phoneList.add("18749966182");
        phoneList.add("19379884788");
        phoneList.add("19335715448");
        phoneList.add("18503558939");
        phoneList.add("13407209608");
        phoneList.add("15596505995");
        phoneList.add("17519874292");
        phoneList.add("15178485516");
        phoneList.add("19877232369");
        phoneList.add("18706287692");
        phoneList.add("18944239644");
        phoneList.add("17325302007");
        phoneList.add("18839074540");
        phoneList.add("19879419704");
        phoneList.add("16480981069");
        phoneList.add("18674257265");
        phoneList.add("18302820904");
        phoneList.add("15133295266");
        phoneList.add("17868457605");
        phoneList.add("15490732767");
        phoneList.add("15064972307");

        phoneNameMap.put("15369468720", "李雁");
        phoneNameMap.put("19920860202", "卫艺");
        phoneNameMap.put("18411925860", "仰莉");
        phoneNameMap.put("14473548449", "陶欣悦");
        phoneNameMap.put("18749966182", "施梅梅");
        phoneNameMap.put("19379884788", "金虹霖");
        phoneNameMap.put("19335715448", "魏明艳");
        phoneNameMap.put("18503558939", "华贞");
        phoneNameMap.put("13407209608", "华啟倩");
        phoneNameMap.put("15596505995", "仲采绿");
        phoneNameMap.put("17519874292", "卫丹");
        phoneNameMap.put("15178485516", "戚丽红");
        phoneNameMap.put("19877232369", "何翠柔");
        phoneNameMap.put("18706287692", "钱溶艳");
        phoneNameMap.put("18944239644", "钱琳");
        phoneNameMap.put("17325302007", "缪静欣");
        phoneNameMap.put("18839074540", "焦秋菊");
        phoneNameMap.put("19879419704", "吕访琴");
        phoneNameMap.put("16480981069", "沈丹");
        phoneNameMap.put("18674257265", "褚美丽");
        phoneNameMap.put("18302820904", "孙怡");
        phoneNameMap.put("15133295266", "许婵");
        phoneNameMap.put("17868457605", "曹红恋");
        phoneNameMap.put("15490732767", "吕柔");
        phoneNameMap.put("15064972307", "冯怜云");


    }


    /**
     * 产生通话数据log
     * 格式:caller,callee,buildTime,duration
     * @return
     */
    public String product(){
        String caller = null;
        String callerName=null;

        String callee = null;
        String calleeName=null;

        int CallerIndex = (int)(Math.random()*phoneList.size());

        //System.out.println("主叫index"+CallerIndex);
        caller = phoneList.get(CallerIndex);
        callerName=phoneNameMap.get(caller);

        for (int i = 0; i <10 ; i++) {

            int CalleeIndex = (int)(Math.random()*phoneList.size());

            //System.out.println("被叫index："+CalleeIndex);
            callee=phoneList.get(CalleeIndex);

            calleeName=phoneNameMap.get(callee);
            if(!callee.equals(caller)) {
                break;
            }
        }



        //第三个字段：通话产生的时间
        String buildTime = ramdomBuildTime(startTime, endTime);

        //第四个字段：通话时长，秒
        DecimalFormat df1 = new DecimalFormat("0000");
        String duration = df1.format((int)(30 * 60 * Math.random()));


        StringBuilder sb = new StringBuilder();

        sb.append(caller+",").append(callee+",").append(buildTime+",").append(duration);

        return sb.toString();
    }

    /**
     * @Author Movle
     * @Description 获取通话产生的时间
     * 公式：startTime+（endTime-startTime ）
     * @Date 4:30 下午 1/28/20
     * @Param [startTime, endTime]
     * @return java.lang.String
     **/
    public String ramdomBuildTime(String startTime, String endTime) {

        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf1.parse(startTime);
            Date endDate = sdf1.parse(endTime);

            if(endDate.getTime() <= startDate.getTime()){
                return null;
            }

            long radomTime = (long) (startDate.getTime() + (endDate.getTime() - startDate.getTime())*Math.random());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ramdomTS = new Date(radomTime);

            String resultTimeLong = sdf2.format(ramdomTS);

            return resultTimeLong;


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeLog(String filepath){
        try {
            OutputStreamWriter ows = new OutputStreamWriter(new FileOutputStream(filepath,true),"UTF-8");
            while(true){

                Thread.sleep(200);
                String log = product();
                System.out.println(log);

                ows.write(log+"\n");
                ows.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //args =new String[]{"/Users/macbook/TestInfo/calllog.csv"};

        if(args==null||args.length<=0){
            System.out.println("没写路径");
            return;
        }

        ProductLog productLog = new ProductLog();

        productLog.initPhone();
        productLog.writeLog(args[0]);


    }
}
