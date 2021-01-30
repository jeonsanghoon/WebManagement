package com.mrc;

import com.mrc.scheduling.addressAreaScheduler;
import com.mrc.scheduling.addressLocationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebManagementApplicationTests {

	@Test
	void contextLoads() throws Exception {
		new addressLocationScheduler().addressLocationUpdateAfterByTempTable();
		//ProjCoordinate rtn = Global.MapInfo.utmkToWgs84(955790.401089, 1952841.038552);
		/*Integer page = 1;
		Integer pageSize = 1000;

		//address_location_cond.builder().build()
		Integer startCode = 1;
		while(true)
		{
			List<t_address_location> list = GlobalDao.addressLocationDao.getList(address_location_cond.builder().startCode(startCode).page_size(pageSize).isXnull(false).build());
			if(list.size() == 0) break;
			for(int i=0; i<list.size(); i++)
			{
				if(!(list.get(i).getX() == null || list.get(i).getY() == null)) {
					ProjCoordinate rtn = Global.getMapInfo().utmkToWgs84(list.get(i).getX(), list.get(i).getY());

					list.get(i).setSearch_latitude(Global.numberInfo.floor(rtn.y, 2));
					list.get(i).setSearch_longitude(Global.numberInfo.floor(rtn.x, 2));
					list.get(i).setLatitude(rtn.y);
					list.get(i).setLongitude(rtn.x);
					//GlobalMapper.AddressLocationMapper.update(list.get(i));
				}
			}

			GlobalMapper.getAddressLocationMapper().updateLatLongiList(list);
			if(list.size() >= pageSize)
			{
				startCode = list.get(pageSize-1).getLocation_code();
				System.out.println("location_code =>" + startCode);
			}
			else System.out.println(" >> 완료 << ");
			page++;
		}

		 */
	}
}
