package com.anish.calabashbros;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Maze {
	private class Node{
		int x;
		int y;
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	private int[][] map;
            
	//锟斤拷锟�
	private int startX;
	private int startY;
	//锟秸碉拷
	private int endX;
	private int endY;
	//锟斤拷锟斤拷锟斤拷锟斤拷牡锟揭伙拷锟�
	public int stepX;
	public int stepY;
	//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥革拷锟斤拷锟斤拷锟竭的凤拷锟斤拷
	private int[] dx = {1,0,-1,0};
	private int[] dy = {0,1,0,-1};
	public Maze(int[][] map, int startX, int startY, int endX, int endY) {
		this.map = map;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	//锟斤拷锟斤拷锟斤拷缺锟斤拷锟窖帮拷锟斤拷怨锟斤拷锟斤拷械锟斤拷锟斤拷锟铰凤拷锟斤拷锟� x,y锟斤拷锟斤拷始锟斤拷
	public void bfs() {
		Deque<Node> quene = new ArrayDeque<Node>();
		//锟芥储每锟斤拷锟斤拷锟角帮拷锟斤拷诘悖�锟斤拷锟斤拷锟接★拷锟斤拷路锟斤拷锟斤拷路锟斤拷
		int[][] pre = new  int[this.map.length][this.map[0].length];
		//锟芥储每锟斤拷锟斤拷锟斤拷锟斤拷路锟斤拷
		int[][] dis = new  int[this.map.length][this.map[0].length];
		for(int i=0; i<dis.length; i++) {
			for(int j=0; j<dis[0].length; j++) {
				dis[i][j] = 100;
			}
		}
		//锟斤拷锟斤拷锟斤拷锟接ｏ拷锟斤拷锟侥撅拷锟斤拷锟斤拷为0锟斤拷锟斤拷锟斤拷锟轿�锟窖凤拷锟斤拷
		quene.add(new Node(this.startX, this.startY));
		dis[this.startX][this.startY] = 0;
		map[this.startX][this.startY] = 2;
		Node temp;
		//锟斤拷锟斤拷锟斤拷缺锟斤拷锟斤拷锟斤拷锌煞锟斤拷实牡悖�锟斤拷锟斤拷锟斤拷每锟斤拷锟斤拷锟斤拷锟斤拷路锟斤拷锟斤拷前锟斤拷锟节碉拷
		while(!quene.isEmpty()) {
			temp = quene.poll();
			//锟斤拷锟斤拷每锟斤拷锟斤拷锟斤拷母锟斤拷锟斤拷锟�
			for(int i=0; i<4; i++) {
				int tx = temp.x + dx[i];
				int ty = temp.y + dy[i];
				//锟斤拷锟斤拷玫锟矫伙拷蟹锟斤拷使锟斤拷锟斤拷锟斤拷玫锟斤拷锟接诧拷锟斤拷锟轿�锟斤拷锟绞癸拷
				if(map[tx][ty] == 0) {
					//锟皆癸拷锟斤拷每锟斤拷只锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟皆撅拷锟斤拷锟揭�
					dis[tx][ty] = dis[temp.x][temp.y] + 1;
					pre[tx][ty] = i;
					map[tx][ty] = 2;
					quene.add(new Node(tx, ty));
				}
			}
		}//锟斤拷锟斤拷锟斤拷dis锟叫达拷诺木锟斤拷锟斤拷锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷pre锟斤拷锟斤拷锟接÷凤拷锟�
		
		int a = this.endX;
		int b = this.endY;
		//System.out.printf("锟斤拷(%d,%d)锟斤拷(%d,%d)锟斤拷锟斤拷叹锟斤拷锟斤拷牵锟�%d锟斤拷路锟斤拷为锟斤拷\n",
		//		this.startX, this.startY, a, b, dis[a][b]);
		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷路锟斤拷锟斤拷路锟竭诧拷锟斤拷栈
		Stack<Node> stack = new Stack<Node>();
		stack.add(new Node(a, b));
		while(a != this.startX || b != this.startY) {
			int da = dx[pre[a][b]];
			int db = dy[pre[a][b]];
			a = a - da;
			b = b - db;
			stack.add(new Node(a,b));
		}
		//锟斤拷栈锟斤拷顺锟斤拷锟斤拷谴锟斤拷锟姐到锟秸碉拷锟铰凤拷锟�
		Node p = stack.pop();
		p = stack.pop();
		stepX = p.x;
		stepY = p.y;
		//System.out.printf("(%d,%d)",stepX,stepY);
	}
}
