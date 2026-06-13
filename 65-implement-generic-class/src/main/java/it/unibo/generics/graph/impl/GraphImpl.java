package it.unibo.generics.graph.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N>{

    private final Map<N, Set<N>> nodes;

    public GraphImpl(){
        this.nodes = new HashMap<>();
    }

    @Override
    public void addNode(final N node) {
        if (node == null) {
            return;
        } else if (this.nodes.containsKey(node)) {
            return;
        } else {
            this.nodes.put(node, new HashSet<>());
        }
    }

    @Override
    public void addEdge(N source, N target) {
        if (source == null || target == null) {
            return;
        }
        if (!this.nodes.containsKey(source) || !this.nodes.containsKey(target)) {
            return;
        }
        this.nodes.get(source).add(target);
    }

    @Override
    public Set<N> nodeSet() {
        return new HashSet<>(this.nodes.keySet());
    }

    @Override
    public Set<N> linkedNodes(N node) {
        if (node == null || !this.nodes.containsKey(node)) {
            return new HashSet<>();
        }
        return new HashSet<N>(this.nodes.get(node));
    }

    @Override
    public List<N> getPath(final N source, final N target) {
        // 1. Controllo di validità: se i nodi sono null o non esistono nel grafo, il cammino non c'è
        if (source == null || target == null || !this.nodes.containsKey(source) || !this.nodes.containsKey(target)) {
            return new LinkedList<>();
        }

        // 2. Prepariamo gli strumenti di cui abbiamo parlato prima
        final Queue<N> queue = new LinkedList<>();      // La coda per l'esplorazione (FIFO)
        final Set<N> visited = new HashSet<>();         // L'insieme per non visitare due volte lo stesso nodo
        final Map<N, N> parentMap = new HashMap<>();    // Mappa per salvare il "padre" di ogni nodo (da dove siamo arrivati)

        // Diamo il via all'algoritmo inserendo il nodo di partenza
        queue.add(source);
        visited.add(source);
        
        boolean found = false;

        // 3. Ciclo principale della BFS
        while (!queue.isEmpty()) {
            final N current = queue.poll(); // Estrae il prossimo nodo in coda

            // Se il nodo corrente è il target, abbiamo trovato il cammino!
            if (current.equals(target)) {
                found = true;
                break; // Interrompiamo la ricerca immediatamente
            }

            // Altrimenti, esploriamo tutti i vicini del nodo corrente
            // Usiamo il metodo linkedNodes(current) che hai scritto tu prima!
            for (final N neighbor : this.linkedNodes(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current); // Registriamo che siamo arrivati a 'neighbor' partendo da 'current'
                    queue.add(neighbor);             // Mettiamo il vicino in coda per essere esplorato dopo
                }
            }
        }

        // 4. Se la coda si svuota e non abbiamo trovato il target, ritorniamo una lista vuota
        if (!found) {
            return new LinkedList<>();
        }

        // 5. Ricostruzione del cammino a ritroso (Il riavvolgimento del nastro)
        final List<N> path = new LinkedList<>();
        N step = target;
        
        // Risaliamo la mappa dei padri partendo dal target fino ad arrivare alla source
        while (step != null) {
            // Usando path.add(0, step) inseriamo l'elemento SEMPRE in testa alla lista.
            // In questo modo, anche se risaliamo al contrario (es. C -> B -> A),
            // nella lista finale l'ordine si raddrizzerà da solo: [A, B, C]!
            path.add(0, step); 
            
            // Se siamo arrivati alla source, il suo padre non sarà nella mappa (sarà null) e il ciclo finirà
            if (step.equals(source)) {
                break;
            }
            step = parentMap.get(step);
        }

        return path;
    }
    
}
