package com.mairwunnx.ui.navigation.graph;

import com.mairwunnx.ui.navigation.types.RouterGraphEntry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
@ApiStatus.Internal
public class RouterGraphImpl implements RouterGraph {
    private final LinkedList<RouterGraphEntry<?>> graph = new LinkedList<>();

    public <T> void push(@NotNull final RouterGraphEntry<T> entry) {
        graph.addLast(entry);
    }

    public void removeLast() {
        if (!graph.isEmpty()) {
            graph.removeLast();
        }
    }

    @Override
    public <T> RouterGraphEntry<T> getLast() {
        if (!graph.isEmpty()) {
            return (RouterGraphEntry<T>) graph.getLast();
        } else {
            return null;
        }
    }

    @Override
    public <T> RouterGraphEntry<T> getFirst() {
        if (!graph.isEmpty()) {
            return (RouterGraphEntry<T>) graph.getFirst();
        } else {
            return null;
        }
    }

    @Override
    @NotNull
    public List<RouterGraphEntry<?>> getPages() {
        return List.copyOf(graph);
    }

    @Override
    public void clear() {
        graph.clear();
    }
}
