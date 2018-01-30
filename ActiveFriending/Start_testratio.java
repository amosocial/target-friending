package ActiveFriending;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Start_testratio {
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//String wiki="data/wiki.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(wiki,8300);
		
		//String gplus="data/gplus.txt";
		//String pokec="data/pokec.txt";
		
		//String HepTh="data/HepTh.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(HepTh,27770);
		
		//String HepPh="data/HepPh.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(HepPh,34546);
		
		String youtube="data/youtube.txt";
		ActiveFriendingInstance myinstance=new ActiveFriendingInstance(youtube,1157900);
		
		//String pokec="data/pokec.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(pokec,1632803);
		
		System.out.println("imported");
		
		int s=myinstance.nodes_by_outdegree[0];
		//int from=3;
		//int to=3;
		int round=3;
		ArrayList<Integer> neighbors=new ArrayList<Integer> ();
		myinstance.get_neighbors(s, round, neighbors,200);
		
		ArrayList<ArrayList<Integer>> resultsRBA=new ArrayList<ArrayList<Integer>>();
		//ArrayList<ArrayList<Integer>> resultsHigh=new ArrayList<ArrayList<Integer>>();
		//ArrayList<ArrayList<Integer>> resultsSP=new ArrayList<ArrayList<Integer>>();
		double p_max;
		double p_RBA;
		int[] appear_node_lists_size=new int[neighbors.size()];
		
		System.out.println("neighbors.size() "+neighbors.size());
		
		int index=200;
		
		PrintWriter writer = new PrintWriter("youtube_"+round+"_ratio_detail.txt");	
		
		for(int i=0;i<index;i++)
		{
			System.out.println("index "+i);
			
			myinstance.set_s_t_index(s, neighbors.get(i));
			//if(myinstance.network.neighbor.get(s).contains(neighbors.get(i)))
			//{
			//	System.out.println("111 ");
			//}
			
			
			
			ArrayList<Integer> resultRBA=new ArrayList<Integer>();
			ArrayList<Integer> appear_node_list=new ArrayList<Integer>();
			System.out.println("RBA");
			p_max=RBA.run(myinstance, resultRBA, appear_node_list);
			
			if(resultRBA.size()<3)
			{
				System.out.println("------------------------------------------------------");
				continue;
			}
			resultsRBA.add(resultRBA);
			appear_node_lists_size[i]=appear_node_list.size();
			System.out.println("testing_probability");
			p_RBA=myinstance.test_probability_rr(resultRBA, p_max);
			System.out.println(resultRBA.size()+" "+p_RBA);
			writer.println(resultRBA.size()+" "+p_RBA);
			
			System.out.println("HighDegree");
			writer.println("HighDegree");
			double p_degree=0;
			int degree_size=resultRBA.size();
			while(p_degree<p_RBA)
			{
				ArrayList<Integer> resultHigh=new ArrayList<Integer>();
				HighDegree.run(myinstance, resultHigh, appear_node_list, degree_size);
				p_degree=myinstance.test_probability_rr(resultHigh, p_max);	
				System.out.println(degree_size+" "+p_degree);
				writer.println(degree_size+" "+p_degree);
				degree_size=degree_size+Math.min(appear_node_list.size()/20, appear_node_list.size()-degree_size);
				
			}
			
			
			System.out.println("SP");
			writer.println("SP");
			double p_SP=0;
			int sp_size=resultRBA.size();
			while(p_SP< p_RBA)
			{
				ArrayList<Integer> resultsp=new ArrayList<Integer>();
				SP.run(myinstance, resultsp, appear_node_list, sp_size);
				p_SP=myinstance.test_probability_rr(resultsp, p_max);	
				System.out.println(sp_size+" "+p_SP);
				writer.println(sp_size+" "+p_SP);
				sp_size=sp_size+Math.min(appear_node_list.size()/50, appear_node_list.size()-sp_size);
				
			}
			
			
			
			//System.out.println("SP");
			//ArrayList<Integer> resultSP=new ArrayList<Integer>();
			//SP.run(myinstance, resultSP, appear_node_list, resultRBA.size());
			//SP.run(myinstance, resultSP, appear_node_list, 200);
			
			
			//System.out.println(appear_node_lists_size[i]+" "+p_maxs[i]+" "+resultsRBA.get(i).size()+" "+p_RBAs[i]);
			//writer.println(appear_node_list.size()+" "+p_max+" "+resultRBA.size()+" "+p_RBA+" "+degree_size+" "+p_degree+" "+sp_size+" "+p_SP);
			//System.out.println("resultRBA: size "+resultRBA.size()+" prob "+myinstance.test_probability(resultRBA, 10000));
			//System.out.println("resultHigh: size "+resultHigh.size()+" prob "+myinstance.test_probability(resultHigh, 10000));
			//System.out.println("resultSP: size "+resultSP.size()+" prob "+myinstance.test_probability(resultSP, 10000));
			System.out.println("------------------------------------------------------");
			writer.println("------------------------------------------------------");
			
		}
		writer.close();

	}
}
