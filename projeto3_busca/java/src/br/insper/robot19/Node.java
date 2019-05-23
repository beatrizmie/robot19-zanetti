package br.insper.robot19;

import br.insper.robot19.block.Block;

public class Node implements Comparable<Node>{
	private Block value;
	private Block end;
	private Node parent;
	private String search;
	private RobotAction action;
	private float pathCost;
	private float heuristic;

	@Override
	public int compareTo(Node node) {
		if(node.getSearch().equals("A")) {
			if (this.getEvaluation() > node.getEvaluation()) {
				return 1;
			} else if (this.getEvaluation() < node.getEvaluation()) {
				return -1;
			} else {
				return 0;
			}
		}else{
			if (this.getHeuristic() > node.getHeuristic()) {
				return 1;
			} else if (this.getHeuristic() < node.getHeuristic()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	private float evaluation;

	public Node (Block value, Node parent, RobotAction action, float cost, Block end, String search) {
		this.value = value;
		this.end = end;
		this.parent = parent;
		this.action = action;
		this.search = search;
		this.pathCost = parent == null ? 0 : parent.getPathCost() + cost;
		this.heuristic = Math.abs(end.row - value.row) + Math.abs(end.col - value.col);
		this.evaluation = this.heuristic + this.pathCost;

	}


	public Block getValue() {
		return value;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public RobotAction getAction() {
		return action;
	}
	
	public float getPathCost() {
		return pathCost;
	}

	public Block getEnd() { return end; }

	public float getHeuristic() { return heuristic; }

	public float getEvaluation() { return evaluation; }

	public String getSearch() { return search; }
}
