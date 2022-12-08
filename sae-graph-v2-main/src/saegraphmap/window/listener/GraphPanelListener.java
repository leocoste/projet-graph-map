package saegraphmap.window.listener;

import saegraphmap.linkedlist.TLieu;

import java.util.ArrayList;

/**
 * Ce listener fait le lien entre la fennettre et le graph panel
 * il est déclanque lorsque l'on clique sur un pts
 */
public interface GraphPanelListener{
    /**
     * @param lieuEvent
     */
    void lieuSelectedChanged(ArrayList<TLieu> lieuEvent);
}
