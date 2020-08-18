from java import jclass
from com.chaquo.python import Python

import matplotlib.pyplot as plt
import numpy as np
import matplotlib.colors as mcolors
import io

def test_contour(width, height, T):
    step = 1
    x = np.arange(0, width, step)
    y = np.arange(0, height, step)
    [X, Y] = np.meshgrid(x, y)
    colors = ["darkblue", "royalblue", "aqua", "springgreen",
              "greenyellow", "yellow", "orangered", "red"]
    clrmap = mcolors.LinearSegmentedColormap.from_list("mycmap", colors)
    plt.contourf(X, Y, T, 48, alpha=1, cmap=clrmap)
    plt.colorbar()
    plt.contour(X, Y, T, 8, colors='black', alpha=0.75, linewidths=0.8, antialiased=True)
    plt.rcParams['figure.figsize'] = (3, 2)
    bio = io.BytesIO()
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b