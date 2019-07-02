import java.io.*;
import java.util.*;

public class LRUAlgorithm {

	public static int blockNumber = 4;				   //블록의 개수를 나타내는 변수 , 현재 블록개수 4개
	
	public static void main(String[] args) {
		int count=0;								   //총 값을 읽은 개수
		int hit =0;									   //적중개수
		String name = "C:\\input.txt";				   //읽을 파일 위치
		Vector<String> v = new Vector<String>();       //파일에서 값을 읽어들여 저장할장소
		String cacheBlock[] = new String[blockNumber]; //메모리블럭
		Queue<Integer> q = new LinkedList<Integer>();  //다음에 나갈 메모리블럭의 위치를 알기위함
		int list[] = new int[blockNumber];			   //위에 queue를 보조
		
		
		for(int i=0;i<cacheBlock.length;i++) 
			cacheBlock[i] = "x"; //블럭 초기화
		
		
		//파일 일기
		try {
			Scanner sc = new Scanner(new FileReader(name));
			
			while(sc.hasNext()) {
				String line = sc.next();
				if(line.equals("99")) break;     //읽은값이 99이면 종료
				v.add(line);					// 읽어온값저장
				count++;						// 개수추가
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("파일경로를 확인해주세요.");
		}
		
		//////////////////////		
		//LRUAlgorithm Start//
		//////////////////////
		
		for(int i=0;i<v.size();i++) {     //들어온 값의 개수만큼 반복
			String nextNumber = v.get(i); //반복문이 돌면서 순서대로 값을 받아옴
			int flag = -1;          //값이 들어갔나를 판별하기위함
									// -1 = 값이 아직안들어감 
									// 0 = 값이 들어감
									// 1 = 이미 있는값이여서 적중
			
			
			//이미 있는값이면 적중
			for(int j=0; j<cacheBlock.length;j++) {				//cacheBlock.length 블록개수만큼반복 
				if(cacheBlock[j].equals(nextNumber)) {			//들어올 값하고 이미있는값하고 같은지 판별
					hit++;                                      //같으면 적중횟수 +1
					flag = 1;									//적중했으니 1로표시
					int q_size = q.size();						//for문돌리기위해 값을 받음
					
					//적중된블록의 번호를 우선순위 맨마지막으로 미루는 작업
					//적중된 번호를 제외하고 한칸씩 앞으로 당긴후 적중된 블록번호는 맨뒤에 넣음
					for(int k=0; k<q_size;k++) {
						list[k] = q.remove();
						if(list[k]!=j) {
							q.add(list[k]);
						}
					}
					q.add(j);
				}
			}
			
			//값 아직 안들어감(이미 있는값이아니라 새로운값) + 빈자리가 있으면 빈자리에 값을넣음
			if(flag == -1) {
				for(int j=0; j<cacheBlock.length;j++) {
					if (cacheBlock[j].equals("x")) {  				//"x"는 빈자리를 의미함
						cacheBlock[j] = nextNumber;					//빈자리이므로 값을 넣어줌
						flag = 0;									//값이들어감  0
						q.add(j);									//queue에 현재 들어간 블록번호를 넣어줌
						break; 										//한번들어가면끝
					}
				}
			}
			
			//빈자리가없으면 값을바꿈
			if(flag == -1) {
				int n = q.poll();									//queue의 맨앞에값을 가져옴 , 맨앞에값은 가장 나중(최근의 반대)에 사용된 블록의 번호가 적혀있음
				cacheBlock[n] = nextNumber;
				q.add(n);											//값이 들어갔으므로 queue에 현재 값이들어간 블록번호를 넣어줌
				flag = 0;
				
			}

			
			// 단계진행할때마다 현재 안에 들어있는 블록번호를 출력함
			for(int j=0; j<cacheBlock.length;j++) {
				System.out.println(j+". "+cacheBlock[j]);
				
			}
			
			//적중시 hit를 출력
			if(flag == 1) {
				System.out.println("hit!!!!!");
			}
			System.out.println("--------------------------------");
		}
			
		
		// 종료시 현재 각각의 캐시블록에 들어있는 주기억장치 블록번호와 적중률을 표시함
		for(int i=0;i<blockNumber;i++)
			System.out.print(cacheBlock[i]+"  ");
		System.out.println("\t적중률은 "+hit+"/"+count);
				
	}

}
