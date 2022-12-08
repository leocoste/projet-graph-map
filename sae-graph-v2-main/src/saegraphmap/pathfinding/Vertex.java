package saegraphmap.pathfinding;

import saegraphmap.linkedlist.TLieu;
import saegraphmap.linkedlist.TRoute;

import java.util.ArrayList;

public class Vertex {
    private ArrayList<TRoute> listRoute = null;

    private final TLieu depart;
    private float distance;

    private int nextVertexIndex;

    Vertex(TLieu depart, float distance, int nextVertexIndex){
        this.depart = depart;
        this.distance = distance;
        this.nextVertexIndex = nextVertexIndex;
    }

    public TLieu getDepart() {
        return depart;
    }

    public int getNextVertexIndex() {
        return nextVertexIndex;
    }

    public float getDistance() {
        return distance;
    }

    public void setNextVertexIndex(int nextVertexIndex) {
        this.nextVertexIndex = nextVertexIndex;
    }

    public ArrayList<TRoute> getListRoute() {
        return listRoute;
    }

    public void setListRoute(ArrayList<TRoute> listRoute) {
        this.listRoute = listRoute;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
