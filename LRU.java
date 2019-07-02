import java.io.*;
import java.util.*;

public class LRUAlgorithm {

	public static int blockNumber = 4;				   //����� ������ ��Ÿ���� ���� , ���� ��ϰ��� 4��
	
	public static void main(String[] args) {
		int count=0;								   //�� ���� ���� ����
		int hit =0;									   //���߰���
		String name = "C:\\input.txt";				   //���� ���� ��ġ
		Vector<String> v = new Vector<String>();       //���Ͽ��� ���� �о�鿩 ���������
		String cacheBlock[] = new String[blockNumber]; //�޸𸮺�
		Queue<Integer> q = new LinkedList<Integer>();  //������ ���� �޸𸮺��� ��ġ�� �˱�����
		int list[] = new int[blockNumber];			   //���� queue�� ����
		
		
		for(int i=0;i<cacheBlock.length;i++) 
			cacheBlock[i] = "x"; //�� �ʱ�ȭ
		
		
		//���� �ϱ�
		try {
			Scanner sc = new Scanner(new FileReader(name));
			
			while(sc.hasNext()) {
				String line = sc.next();
				if(line.equals("99")) break;     //�������� 99�̸� ����
				v.add(line);					// �о�°�����
				count++;						// �����߰�
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("���ϰ�θ� Ȯ�����ּ���.");
		}
		
		//////////////////////		
		//LRUAlgorithm Start//
		//////////////////////
		
		for(int i=0;i<v.size();i++) {     //���� ���� ������ŭ �ݺ�
			String nextNumber = v.get(i); //�ݺ����� ���鼭 ������� ���� �޾ƿ�
			int flag = -1;          //���� ������ �Ǻ��ϱ�����
									// -1 = ���� �����ȵ� 
									// 0 = ���� ��
									// 1 = �̹� �ִ°��̿��� ����
			
			
			//�̹� �ִ°��̸� ����
			for(int j=0; j<cacheBlock.length;j++) {				//cacheBlock.length ��ϰ�����ŭ�ݺ� 
				if(cacheBlock[j].equals(nextNumber)) {			//���� ���ϰ� �̹��ִ°��ϰ� ������ �Ǻ�
					hit++;                                      //������ ����Ƚ�� +1
					flag = 1;									//���������� 1��ǥ��
					int q_size = q.size();						//for������������ ���� ����
					
					//���ߵȺ���� ��ȣ�� �켱���� �Ǹ��������� �̷�� �۾�
					//���ߵ� ��ȣ�� �����ϰ� ��ĭ�� ������ ����� ���ߵ� ��Ϲ�ȣ�� �ǵڿ� ����
					for(int k=0; k<q_size;k++) {
						list[k] = q.remove();
						if(list[k]!=j) {
							q.add(list[k]);
						}
					}
					q.add(j);
				}
			}
			
			//�� ���� �ȵ�(�̹� �ִ°��̾ƴ϶� ���ο) + ���ڸ��� ������ ���ڸ��� ��������
			if(flag == -1) {
				for(int j=0; j<cacheBlock.length;j++) {
					if (cacheBlock[j].equals("x")) {  				//"x"�� ���ڸ��� �ǹ���
						cacheBlock[j] = nextNumber;					//���ڸ��̹Ƿ� ���� �־���
						flag = 0;									//���̵�  0
						q.add(j);									//queue�� ���� �� ��Ϲ�ȣ�� �־���
						break; 										//�ѹ����鳡
					}
				}
			}
			
			//���ڸ��������� �����ٲ�
			if(flag == -1) {
				int n = q.poll();									//queue�� �Ǿտ����� ������ , �Ǿտ����� ���� ����(�ֱ��� �ݴ�)�� ���� ����� ��ȣ�� ��������
				cacheBlock[n] = nextNumber;
				q.add(n);											//���� �����Ƿ� queue�� ���� ���̵� ��Ϲ�ȣ�� �־���
				flag = 0;
				
			}

			
			// �ܰ������Ҷ����� ���� �ȿ� ����ִ� ��Ϲ�ȣ�� �����
			for(int j=0; j<cacheBlock.length;j++) {
				System.out.println(j+". "+cacheBlock[j]);
				
			}
			
			//���߽� hit�� ���
			if(flag == 1) {
				System.out.println("hit!!!!!");
			}
			System.out.println("--------------------------------");
		}
			
		
		// ����� ���� ������ ĳ�ú�Ͽ� ����ִ� �ֱ����ġ ��Ϲ�ȣ�� ���߷��� ǥ����
		for(int i=0;i<blockNumber;i++)
			System.out.print(cacheBlock[i]+"  ");
		System.out.println("\t���߷��� "+hit+"/"+count);
				
	}

}
