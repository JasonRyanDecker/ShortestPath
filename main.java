import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ShortestPathGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton runButton;
    private JTextArea outputTextArea;
    private JTextField inputField;

    public ShortestPathGUI() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        runButton = new JButton("Run");
        runButton.addActionListener(e -> runShortestPath());

        inputField = new JTextField();
        inputField.setColumns(20);

        outputTextArea = new JTextArea();
        outputTextArea.setRows(10);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        panel.add(inputField, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(runButton, BorderLayout.SOUTH);

        add(panel);
        setTitle("Shortest Path Algorithm");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void runShortestPath() {
        String input = inputField.getText();
        String[] inputArray = input.split("\\s+"); // Split input by whitespace
        int n = Integer.parseInt(inputArray[0]); // Number of nodes
        int m = Integer.parseInt(inputArray[1]); // Number of edges
        int source = Integer.parseInt(inputArray[2]); // Source node
        int dest = Integer.parseInt(inputArray[3]); // Destination node
        
        // Create the graph as an adjacency list
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Add edges to the graph
        for (int i = 0; i < m; i++) {
            int u = Integer.parseInt(inputArray[4 + 3*i]);
            int v = Integer.parseInt(inputArray[5 + 3*i]);
            int w = Integer.parseInt(inputArray[6 + 3*i]);
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w)); // Assuming undirected graph
        }
        
        // Run Dijkstra's algorithm to find the shortest path
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.u;
            int d = node.d;
            if (d > dist[u]) {
                continue;
            }
            for (Edge edge : graph.get(u)) {
                int v = edge.v;
                int w = edge.w;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
        
        // Output the shortest path
        String output = "Shortest path: " + dist[dest];
        outputTextArea.setText(output);
    }
    
    private static class Edge {
        int v;
        int w;
        
        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
    
    private static class Node implements Comparable<Node> {
        int u;
        int d;
        
        Node(int u, int d) {
            this.u = u;
            this.d = d;
        }
        
        @Override
        public int compareTo(Node other) {
           
