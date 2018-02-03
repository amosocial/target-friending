package ActiveFriending;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Start {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int round=2;
		String wiki="data/wiki.txt";
		ActiveFriendingInstance myinstance=new ActiveFriendingInstance(wiki,8300);
		PrintWriter writer = new PrintWriter("wiki_"+round+".txt");
		
		//String gplus="data/gplus.txt";
		//String pokec="data/pokec.txt";
		
		//String HepTh="data/HepTh.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(HepTh,27770);
		
		//String HepPh="data/HepPh.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(HepPh,34546);
		
		//String youtube="data/youtube.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(youtube,1157900);
		//PrintWriter writer = new PrintWriter("youtube_"+round+".txt");
		
		//String gplus="data/gplus.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(gplus,107614);
		//PrintWriter writer = new PrintWriter("gplus_"+round+".txt");
		
		//String pokec="data/pokec.txt";
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(pokec,1632803);
		//PrintWriter writer = new PrintWriter("pokec_"+round+".txt");
		
		System.out.println("imported");
		
		int s=myinstance.nodes_by_outdegree[0];
		//int from=3;
		//int to=3;
		
		ArrayList<Integer> neighbors=new ArrayList<Integer> ();
		myinstance.get_neighbors(s, round, neighbors,50);
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(pokec,1632803);
		
		
		
		//PrintWriter writer = new PrintWriter("pokec_"+round+".txt");	
		
		//PrintWriter writer = new PrintWriter("youtube_"+round+"_06.txt");
		
		
		
		
		
		
		
		ArrayList<ArrayList<Integer>> resultsRBA=new ArrayList<ArrayList<Integer>>();
		//ArrayList<ArrayList<Integer>> resultsHigh=new ArrayList<ArrayList<Integer>>();
		//ArrayList<ArrayList<Integer>> resultsSP=new ArrayList<ArrayList<Integer>>();
		double[] p_maxs=new double[neighbors.size()];
		double[] p_RBAs=new double[neighbors.size()];
		int[] appear_node_lists_size=new int[neighbors.size()];
		
		System.out.println("neighbors.size() "+neighbors.size());
		
		for(int i=0;i<neighbors.size();i++)
		{
			System.out.println(i+" "+neighbors.get(i));
			myinstance.set_s_t_index(s, neighbors.get(i));
			//if(myinstance.network.neighbor.get(s).contains(neighbors.get(i)))
			//{
			//	System.out.println("111 ");
			//}
			
			
			
			ArrayList<Integer> resultRBA=new ArrayList<Integer>();
			ArrayList<Integer> appear_node_list=new ArrayList<Integer>();
			System.out.println("RBA");
			p_maxs[i]=RBA.run(myinstance, resultRBA, appear_node_list);
			resultsRBA.add(resultRBA);
			appear_node_lists_size[i]=appear_node_list.size();
			System.out.println("testing_probability");
			p_RBAs[i]=myinstance.test_probability_rr(resultRBA, p_maxs[i]);
			
			
			//System.out.println("HighDegree");
			//ArrayList<Integer> resultHigh=new ArrayList<Integer>();
			//HighDegree.run(myinstance, resultHigh, appear_node_list, resultRBA);
			
			//System.out.println("SP");
			//ArrayList<Integer> resultSP=new ArrayList<Integer>();
			//SP.run(myinstance, resultSP, appear_node_list, resultRBA.size());
			//SP.run(myinstance, resultSP, appear_node_list, 200);
			
			
			System.out.println(appear_node_lists_size[i]+" "+p_maxs[i]+" "+resultsRBA.get(i).size()+" "+p_RBAs[i]);
			writer.println(neighbors.get(i)+" "+appear_node_lists_size[i]+" "+p_maxs[i]+" "+resultsRBA.get(i).size()+" "+p_RBAs[i]);
			//System.out.println("resultRBA: size "+resultRBA.size()+" prob "+myinstance.test_probability(resultRBA, 10000));
			//System.out.println("resultHigh: size "+resultHigh.size()+" prob "+myinstance.test_probability(resultHigh, 10000));
			//System.out.println("resultSP: size "+resultSP.size()+" prob "+myinstance.test_probability(resultSP, 10000));
			System.out.println("------------------------------------------------------");
			
		}
		writer.close();
		for(int i=0;i<neighbors.size();i++)
		{
			System.out.println(s);
			System.out.println(neighbors.get(i)+" "+appear_node_lists_size[i]+" "+p_maxs[i]+" "+resultsRBA.get(i).size()+" "+p_RBAs[i]);
		}
		
		
	}

}
