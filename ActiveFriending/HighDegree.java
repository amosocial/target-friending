package ActiveFriending;

import java.util.ArrayList;

public class HighDegree {
	public static void run(ActiveFriendingInstance myinstance, ArrayList<Integer> result, ArrayList<Integer> appear_node_list, int size)
	{
		
		result.add(myinstance.t);
		for(int i=0;i<myinstance.network.vertexNum;i++)
		{
			if(result.size()==size)
			{
				return;
			}
			if(appear_node_list.contains(myinstance.nodes_by_outdegree[i]))
			{
				result.add(myinstance.nodes_by_outdegree[i]);
			}
		}
	}
}
