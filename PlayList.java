/**
 * Represnts a list of musical tracks. The list has a maximum capacity (int),
 * and an actual size (number of tracks in the list, an int).
 */
class PlayList {
    private Track[] tracks; // Array of tracks (Track objects)
    private int maxSize; // Maximum number of tracks in the array
    private int size; // Actual number of tracks in the array

    /** Constructs an empty play list with a maximum number of tracks. */
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        tracks = new Track[maxSize];
        size = 0;
    }

    /** Returns the maximum size of this play list. */
    public int getMaxSize() {
        return maxSize;
    }

    /** Returns the current number of tracks in this play list. */
    public int getSize() {
        return size;
    }

    /** Method to get a track by index */
    public Track getTrack(int index) {
        if (index >= 0 && index < size) {
            return tracks[index];
        } else {
            return null;
        }
    }

    /**
     * Appends the given track to the end of this list.
     * If the list is full, does nothing and returns false.
     * Otherwise, appends the track and returns true.
     */
    public boolean add(Track track) {
        if (this.size == this.maxSize) {
            return false;
        }
        this.tracks[size] = track;
        this.size = size + 1;
        return true;
    }

    /**
     * Returns the data of this list, as a string. Each track appears in a separate
     * line.
     */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {
        String stringBuilder = "";
        for (int i = 0; i < this.size; i++) {
            stringBuilder += "\n" + this.getTrack(i);
        }
        return stringBuilder;
    }

    /**
     * Removes the last track from this list. If the list is empty, does nothing.
     */
    public void removeLast() {
        if (this.size > 0) {
            this.tracks[size - 1] = null;
            this.size = size - 1;
        }
    }

    /** Returns the total duration (in seconds) of all the tracks in this list. */
    public int totalDuration() {
        int sumOfDuration = 0;
        for (int i = 0; i < tracks.length; i++) {
            sumOfDuration += tracks[i].getDuration();
        }
        return sumOfDuration;
    }

    /**
     * Returns the index of the track with the given title in this list.
     * If such a track is not found, returns -1.
     */
    public int indexOf(String title) {
        title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i].getTitle().equals(title))
                ;
            return i;
        }
        return -1;
    }

    /**
     * Inserts the given track in index i of this list. For example, if the list is
     * (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     * If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     * If i is negative or greater than the size of this list, or if the list
     * is full, does nothing and returns false. Otherwise, inserts the track and
     * returns true.
     */
    public boolean add(int i, Track track) {
        // Check if i is negative or greater than the size of the list
        if (i < 0 || i > this.size || i >= this.maxSize) {
            return false;
        }
        // Shift elements to the right to make space for the new element
        for (int j = this.size - 1; j >= i; j--) {
            this.tracks[j + 1] = this.tracks[j];
        }
        // Insert the track at index i
        this.tracks[i] = track;
        this.size++; // Increment the size of the list
        return true;
    }

    /**
     * Removes the track in the given index from this list.
     * If the list is empty, or the given index is negative or too big for this
     * list,
     * does nothing and returns -1.
     */
    public void remove(int i) {
        if (this.size == 0 || i < 0 || i >= this.size) {
            return;
        }
        if (i == this.size - 1) {
            this.removeLast();
        } else {
            this.tracks[i] = null; // Removes the track in the i index fron the list 
            this.size = size - 1; // Decreasing the actual number of tracks by 1
            // “Closes the gap” in the array by moving all the tracks on the right of the deleted track one step to the left
            for (int j = i; j <= this.size; j++) {
                this.tracks[j] = this.tracks[j + 1];
            }
        }
    }

    /**
     * Removes the first track that has the given title from this list.
     * If such a track is not found, or the list is empty, or the given index
     * is negative or too big for this list, does nothing.
     */
    public void remove(String title) {
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i].getTitle().equals(title)) {
                this.tracks[i] = null;
            }
        }
        return;
    }

    /**
     * Removes the first track from this list. If the list is empty, does nothing.
     */
    public void removeFirst() {
        if (tracks.length == 0) {
            return;
        }
        Track[] newTracks = new Track[tracks.length - 1];
        for (int i = 1; i < tracks.length; i++) {
            newTracks[i - 1] = tracks[i];
        }
        tracks = newTracks;
    }

    /**
     * Adds all the tracks in the other list to the end of this list.
     * If the total size of both lists is too large, does nothing.
     */
    //// An elegant and terribly inefficient implementation.
    public void add(PlayList other) {
        if ((this.size + other.size) > this.maxSize) {
            return;
        }
        for (int i = 0; i < tracks.length; i++) {
            size++;
            tracks[size + i] = other.tracks[i];
        }
    }

    /**
     * Returns the index in this list of the track that has the shortest duration,
     * starting the search in location start. For example, if the durations are
     * 7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the
     * minimum value (5) when starting the search from index 2.
     * If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {
        if (start >= 0 && start < this.size) {
            int index = start; // will be updated to the index of the shortest track found
            int min = getTrack(index).getDuration(); // stores the duration of the shortest track found so far
            for (int i = start; i < this.size; i++) {
                if (this.getTrack(i).getDuration() < min) {
                    index = i;
                    min = getTrack(i).getDuration();
                }
            }
            return index;
        }
        return -1;
    }

    /**
     * Returns the title of the shortest track in this list.
     * If the list is empty, returns null.
     */
    public String titleOfShortestTrack() {
        return tracks[minIndex(0)].getTitle();
    }

    /**
     * Sorts this list by increasing duration order: Tracks with shorter
     * durations will appear first. The sort is done in-place. In other words,
     * rather than returning a new, sorted playlist, the method sorts
     * the list on which it was called (this list).
     */
    public void sortedInPlace() {
        // Uses the selection sort algorithm,
        for (int i = 0; i < this.size; i++) {
            int min = this.minIndex(i); // calling the minIndex method in each iteration
            // Swaps between track[i] and track[min] in the tracks array
            if (i != min) {
                Track temp = tracks[i];
                tracks[i] = tracks[min];
                tracks[min] = temp;
            }
        }
    }
}
