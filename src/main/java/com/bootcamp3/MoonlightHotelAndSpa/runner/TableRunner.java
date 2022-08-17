//package com.bootcamp3.MoonlightHotelAndSpa.runner;
//
//import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
//import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
//import com.bootcamp3.MoonlightHotelAndSpa.service.TableService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TableRunner implements CommandLineRunner {
//
//    private final TableService tableService;
//
//    @Autowired
//    public TableRunner(TableService tableService) {
//        this.tableService = tableService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        //BAR
//        createTable(TableZone.BAR, 0, 11, false);
//
//        //LOUNGE
//        //Tables 1 to 5
//        for (int i = 1; i <= 5; i++) {
//
//            createTable(TableZone.LOUNGE, i, 4, false);
//        }
//
//        //Table 6
//        createTable(TableZone.LOUNGE, 6, 8, false);
//
//        //Table 7,8
//        createTable(TableZone.LOUNGE, 7, 7, false);
//        createTable(TableZone.LOUNGE, 8, 7, false);
//
//        //Table 9, 10
//        createTable(TableZone.LOUNGE, 9, 2, false);
//        createTable(TableZone.LOUNGE, 10, 2, false);
//
//        //TERRACE
//        //Table 11 to 16
//        for (int i = 11; i <= 16; i++) {
//
//            createTable(TableZone.TERRACE, i, 4, true);
//        }
//    }
//
//    private void createTable(TableZone zone, int number, int people, boolean smoking) {
//
//        Table table = new Table();
//        table.setZone(zone);
//        table.setNumber(number);
//        table.setPeople(people);
//        table.setSmoking(smoking);
//
//        tableService.save(table);
//    }
//}
