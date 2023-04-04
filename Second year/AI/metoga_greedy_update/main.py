import os

import numpy as np
import networkx as nx
import matplotlib.pyplot as plt
#import plotly.graph_objects as go


# read the network details
def readNet(fileName):
    f = open(fileName, "r")
    net = {}
    n = int(f.readline())
    net['noNodes'] = n
    mat = []
    for i in range(n):
        mat.append([])
        line = f.readline()
        elems = line.split(" ")
        for j in range(n):
            mat[-1].append(int(elems[j]))
    net["mat"] = mat
    degrees = []
    noEdges = 0
    for i in range(n):
        d = 0
        for j in range(n):
            if (mat[i][j] == 1):
                d += 1
            if (j > i):
                noEdges += mat[i][j]
        degrees.append(d)
    net["noEdges"] = noEdges
    net["degrees"] = degrees
    f.close()
    #print(net)
    return net


def greedyCommunitiesDetectionByTool(G):
    # Input: a graph
    # Output: list of comunity index (for every node)

    from networkx.algorithms import community

#    A=np.matrix(network["mat"])
#    G=nx.from_numpy_matrix(A)
    communities_generator = community.girvan_newman(G)
    top_level_communities = next(communities_generator)
    sorted(map(sorted, top_level_communities))
    communities = [0 for node in range(len(G.nodes))]
    index = 1
    for community in sorted(map(sorted, top_level_communities)):
        try:
            for node in community:
                communities[node] = index
            index += 1
        except: pass
    return communities


def edge_to_remove(G):
    # măsoară numărul de drumuri cele mai scurte care trec printr-o anumită
    # muchie într-o rețea și calculează cât de mult acea muchie este implicată în aceste drumuri.
    tuple = nx.edge_betweenness_centrality(G)
    tuple_list = list(tuple.items())
    tuple_list.sort(key=lambda x: x[1], reverse=True)
    #print(centrList[0][0])
    return tuple_list[0][0]

def girvan(g, nr):
 #   a = np.matrix(network["mat"])
 #   g = nx.from_numpy_matrix(g)
    a = nx.connected_components(g) #componente conexe
    lena = len(list(a))
    while lena < nr:
        #stergem muchii ca sa construim comunitatiile
        # We need (a,b) instead of ((a,b))
        u, v = edge_to_remove(g)
        g.remove_edge(u, v)

        a = nx.connected_components(g)
        lena = len(list(a))
    n = len(g.nodes)
    ming = min(list(g.nodes))
    communities = [0] * n
    index = 1

    #impartim nodurile in comunitati
    for i in range(ming, n):
        for j in range(ming, i):
             if i in nx.node_connected_component(g, j):
                communities[i] = communities[j]
        if communities[i] == 0:
            communities[i] = index
            index += 1
    return communities


def plotNetwork(G, communities=None):
    if communities is None:
        communities = [1, 1, 1, 1, 1, 1]
    np.random.seed(123) #to freeze the graph's view (networks uses a random view)
    #A=np.matrix(network["mat"])
    #G=nx.from_numpy_matrix(A)
    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu, node_color =list(communities))
    nx.draw_networkx_edges(G, pos, alpha=0.3)

    plt.show()

def num_communities(G):
    from networkx.algorithms import community
    comp = community.girvan_newman(G)
    communities = sorted(next(comp))
    print(communities)
    return len(communities)

def run():
    # load a network
    crtDir = os.getcwd()
    #filePath = os.path.join(crtDir, 'data\\given\\karate', 'karate.gml')
    filePath0 = os.path.join(crtDir, 'data\\given\\dolphins', 'dolphins.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, 2)
    plotNetwork(network0, comunities_implemented0)

    filePath0 = os.path.join(crtDir, 'data\\given\\football', 'football.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, 12)
    plotNetwork(network0, comunities_implemented0)

    filePath0 = os.path.join(crtDir, 'data\\given\\karate', 'karate.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, 1)
    plotNetwork(network0, comunities_implemented0)

    filePath0 = os.path.join(crtDir, 'data\\given\\krebs', 'krebs.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, 3)
    plotNetwork(network0, comunities_implemented0)

    filePath0 = os.path.join(crtDir, 'data\\searched', 'adjnoun.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, 10)
    plotNetwork(network0, comunities_implemented0)

    filePath0 = os.path.join(crtDir, 'data\\searched', 'lesmis.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, len(set(network0)))
    plotNetwork(network0, comunities_implemented0)

    filePath0 = os.path.join(crtDir, 'data\\searched', 'polbooks.gml')
    network0 = nx.read_gml(filePath0, label='id')
    comunities_implemented0 = girvan(network0, len(set(network0)))
    plotNetwork(network0, comunities_implemented0)
    # plot the network
    #plotNetwork(network)
    #comunities_byTool=greedyCommunitiesDetectionByTool(network)

    #print(comunities_byTool)

    #print(num_communities(network))
    #plotNetwork(network, comunities_byTool)

run()