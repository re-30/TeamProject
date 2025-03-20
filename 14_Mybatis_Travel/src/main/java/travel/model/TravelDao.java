package travel.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import utility.Paging;

//@Component("myTravelDao")
@Service("myTravelDao")
public class TravelDao {
		
		private String  namespace="travel.TravelBean";
		
		@Autowired
		SqlSessionTemplate sqlSessionTemplate;
		//rootContext xml에서 만든 객체 주입시켜라
		
		public TravelDao() {
			
		}
		
		public List<TravelBean> getAllList( Paging pageInfo,Map<String,String> map){
			
			
			RowBounds rowBounds = new RowBounds(pageInfo.getOffset(),pageInfo.getLimit());
			//레코드를 건너뛰게 해줄 수 있는 객체
			List<TravelBean> lists = sqlSessionTemplate.selectList(namespace+".getTravelList",map,rowBounds);
			return lists;
			
		}//getall

		public int insertTravel(TravelBean travel) {
			
			int cnt = sqlSessionTemplate.insert(namespace+".insertTravel",travel);
			return cnt;
		}//insert
		
		public int deleteTravel(int num) {
			
				int cnt = sqlSessionTemplate.delete("travel.TravelBean.deleteTravel",num);
			return cnt;
		}//delete
		
		public TravelBean getOneSelect(int num) {
			
				TravelBean travel = sqlSessionTemplate.selectOne("travel.TravelBean.getOneTravel",num);
			return travel;
		}//get
		
		public int updateTravel(TravelBean travel) {
			
			int cnt = sqlSessionTemplate.update("travel.TravelBean.updateTravel",travel);
			return cnt;
		}//up
		public int getTotalCount(Map<String,String> map) {
			int totalCount = sqlSessionTemplate.selectOne(namespace+".getTotalCount",map);
			
			return totalCount;
		}
}
