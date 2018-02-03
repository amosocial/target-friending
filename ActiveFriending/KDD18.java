package ActiveFriending;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class KDD18 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		String path=args[0];
		int vNum=Integer.parseInt(args[1]);
		int initiator=Integer.parseInt(args[2]);
		int target=Integer.parseInt(args[3]);
			
		ActiveFriendingInstance myinstance=new ActiveFriendingInstance(path,vNum);
		
		System.out.println("dataset imported");
		
		System.out.println("initiator: "+initiator);
		System.out.println("target: "+target);
		
		
		ArrayList<Integer> neighbors=new ArrayList<Integer> ();
		neighbors.add(target);

		double p_maxs;
		double p_RBAs;
		
		myinstance.set_s_t_index(initiator, target);		
		ArrayList<Integer> resultRBA=new ArrayList<Integer>();
		ArrayList<Integer> appear_node_list=new ArrayList<Integer>();
		System.out.println("Running...may take minutes on wiki and hepth, and hours on large dataset");
		p_maxs=RBA.run(myinstance, resultRBA, appear_node_list);
		int appear_node_lists_size=appear_node_list.size();
		System.out.println("testing_probability");
		p_RBAs=myinstance.test_probability_rr(resultRBA, p_maxs);

		
		System.out.println("Done----------");			
		System.out.println("initiator: "+initiator);
		System.out.println("target: "+target);
		System.out.println("|A(s,t)|: "+appear_node_lists_size);
		System.out.println("p_max: "+p_maxs);
		System.out.println("|I^*|: "+resultRBA.size());
		System.out.println("f(I^*): "+p_RBAs);
		System.out.print("Invited nodes: ");
		for(int i=0;i<resultRBA.size();i++)
		{
			System.out.print(resultRBA.get(i)+" ");
		}
		System.out.println();

		
		
	}

}
