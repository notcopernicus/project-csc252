public class ReservationTree {
    private class Node {
        Reservation reservation;
        Node left, right;
        int height;

        Node(Reservation res) {
            this.reservation = res;
            this.height = 1;
        }
    }

    private Node root;

    // The logic to Prevent Conflicts [cite: 183, 190]
    private boolean hasOverlap(Node node, int newStart, int newEnd) {
        if (node == null) return false;

        // Condition for interval overlap: 
        // (StartA < EndB) AND (EndA > StartB)
        if (node.reservation.getStart() < newEnd && node.reservation.getEnd() > newStart) {
            return true;
        }

        // Search based on start time
        if (newStart < node.reservation.getStart()) {
            return hasOverlap(node.left, newStart, newEnd);
        } else {
            return hasOverlap(node.right, newStart, newEnd);
        }
    }

    public boolean addReservation(Reservation res) {
        // First check for conflicts 
        if (hasOverlap(root, res.getStart(), res.getEnd())) {
            System.out.println("Conflict detected! Cannot schedule.");
            return false;
        }

        root = insert(root, res);
        return true;
    }

    private Node insert(Node node, Reservation res) {
        // 1. Standard BST insertion logic based on Start Time
        if (node == null) return new Node(res);
        
        if (res.getStart() < node.reservation.getStart()) {
            node.left = insert(node.left, res);
        } else {
            node.right = insert(node.right, res);
        }

        // 2. Update height and perform AVL balancing rotations here
    
        
        return node; 
    }
}