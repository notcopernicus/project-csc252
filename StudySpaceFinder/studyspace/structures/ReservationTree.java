package studyspace.structures;

import studyspace.model.Reservation;
import java.util.ArrayList;
import java.util.List;

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

    public ReservationTree() {}

    public boolean addReservation(Reservation res) {
        if (hasOverlap(root, res.getRoomID(), res.getStartTime(), res.getEndTime())) {
            System.out.println("Conflict detected for Room " + res.getRoomID() +
                               "! Reservation " + res.getReservationID() + " cannot be scheduled.");
            return false;
        }
        root = insert(root, res);
        return true;
    }

    public boolean cancelReservation(String reservationID) {
        if (!exists(root, reservationID)) {
            System.out.println("Reservation " + reservationID + " not found.");
            return false;
        }
        root = delete(root, reservationID);
        return true;
    }

    public void cancelOverlapping(String roomID, String startTime, String endTime) {
        List<Reservation> overlapping = new ArrayList<>();
        collectOverlapping(root, roomID, startTime, endTime, overlapping);

        if (overlapping.isEmpty()) {
            System.out.println("No overlapping reservations found in " + roomID + " for that window.");
            return;
        }

        for (Reservation r : overlapping) {
            root = delete(root, r.getReservationID());
            System.out.println("Cancelled overlapping reservation: " + r.getReservationID());
        }
    }

    private void collectOverlapping(Node node, String roomID, String start, String end,
                                    List<Reservation> result) {
        if (node == null) return;
        collectOverlapping(node.left, roomID, start, end, result);
        if (node.reservation.getRoomID().equals(roomID)) {
            if (start.compareTo(node.reservation.getEndTime()) < 0 &&
                end.compareTo(node.reservation.getStartTime()) > 0) {
                result.add(node.reservation);
            }
        }
        collectOverlapping(node.right, roomID, start, end, result);
    }

    public List<Reservation> getReservationsForRoom(String roomID) {
        List<Reservation> result = new ArrayList<>();
        inOrderByRoom(root, roomID, result);
        return result;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    public void displayAll() {
        List<Reservation> all = getAllReservations();
        if (all.isEmpty()) {
            System.out.println("No reservations scheduled.");
        } else {
            System.out.println("--- All Reservations ---");
            for (Reservation r : all) {
                System.out.println("  " + r);
            }
        }
    }

    private boolean hasOverlap(Node node, String roomID, String newStart, String newEnd) {
        if (node == null) return false;

        if (node.reservation.getRoomID().equals(roomID)) {
            if (newStart.compareTo(node.reservation.getEndTime()) < 0 &&
                newEnd.compareTo(node.reservation.getStartTime()) > 0) {
                return true;
            }
        }

        return hasOverlap(node.left, roomID, newStart, newEnd) ||
               hasOverlap(node.right, roomID, newStart, newEnd);
    }

    private Node insert(Node node, Reservation res) {
        if (node == null) return new Node(res);

        if (res.getStartTime().compareTo(node.reservation.getStartTime()) < 0) {
            node.left = insert(node.left, res);
        } else {
            node.right = insert(node.right, res);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return rebalance(node);
    }

    private Node delete(Node node, String reservationID) {
        if (node == null) return null;

        if (node.reservation.getReservationID().equals(reservationID)) {
            if (node.left == null)  return node.right;
            if (node.right == null) return node.left;

            Node successor = findMin(node.right);
            node.reservation = successor.reservation;
            node.right = delete(node.right, successor.reservation.getReservationID());
        } else {
            node.left  = delete(node.left,  reservationID);
            node.right = delete(node.right, reservationID);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return rebalance(node);
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rebalance(Node node) {
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node y) {
        Node x  = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left  = T2;
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y  = x.right;
        Node T2 = y.left;
        y.left  = x;
        x.right = T2;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private void inOrder(Node node, List<Reservation> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.reservation);
        inOrder(node.right, result);
    }

    private void inOrderByRoom(Node node, String roomID, List<Reservation> result) {
        if (node == null) return;
        inOrderByRoom(node.left, roomID, result);
        if (node.reservation.getRoomID().equals(roomID)) {
            result.add(node.reservation);
        }
        inOrderByRoom(node.right, roomID, result);
    }

    private boolean exists(Node node, String reservationID) {
        if (node == null) return false;
        if (node.reservation.getReservationID().equals(reservationID)) return true;
        return exists(node.left, reservationID) || exists(node.right, reservationID);
    }
}