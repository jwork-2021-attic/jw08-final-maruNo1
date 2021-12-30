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
            
	private int startX;
	private int startY;

	private int endX;
	private int endY;
	
	public int stepX;
	public int stepY;
	private int[] dx = {1,0,-1,0};
	private int[] dy = {0,1,0,-1};
	public Maze(int[][] map, int startX, int startY, int endX, int endY) {
		this.map = map;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	public void bfs() {
		Deque<Node> quene = new ArrayDeque<Node>();
		int[][] pre = new  int[this.map.length][this.map[0].length];
		int[][] dis = new  int[this.map.length][this.map[0].length];
		for(int i=0; i<dis.length; i++) {
			for(int j=0; j<dis[0].length; j++) {
				dis[i][j] = 100;
			}
		}
		quene.add(new Node(this.startX, this.startY));
		dis[this.startX][this.startY] = 0;
		map[this.startX][this.startY] = 2;
		Node temp;
		while(!quene.isEmpty()) {
			temp = quene.poll();
			for(int i=0; i<4; i++) {
				int tx = temp.x + dx[i];
				int ty = temp.y + dy[i];
				if(map[tx][ty] == 0) {
					dis[tx][ty] = dis[temp.x][temp.y] + 1;
					pre[tx][ty] = i;
					map[tx][ty] = 2;
					quene.add(new Node(tx, ty));
				}
			}
		}
		int a = this.endX;
		int b = this.endY;

		Stack<Node> stack = new Stack<Node>();
		stack.add(new Node(a, b));
		while(a != this.startX || b != this.startY) {
			int da = dx[pre[a][b]];
			int db = dy[pre[a][b]];
			a = a - da;
			b = b - db;
			stack.add(new Node(a,b));
		}
		Node p = stack.pop();
		p = stack.pop();
		stepX = p.x;
		stepY = p.y;
		
	}
}
