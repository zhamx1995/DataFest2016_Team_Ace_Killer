# DataFest2016_Team_Ace_Killer
Team members: Mingxuan Zha, Shuhao Hu, Yuhan Wu, Yuansong Feng, Yuanling Wang

The major aim of our study is to distinguish scalpers from true fans. We built up a model with possible available factors: venue location, number of tickets in a purchase, time between purchase and the event, average price of ticket in purchase, and how user get the tickets.

Our assumption is, the probability of a user being a scalper increases when: the venue city is a populated city, one purchase includes many tickets, there is a huge time gap between the event and the time of purchase, the ticket price is high, and when user choose not to print the ticket. We are sure that a user is a scalper if he/she purchases tickets of multiple events that are occurring at the same time.

Mainly utilizing Java programming, we went through the following procedure. First of all, we integrated data from purchase data file by categorizing them first by events, and then by persons, namely we obtained the data for each particular purchase party. Afterwards, for each factor discussed above, we developed a criterion to quantify the factor into an integer from 1 to 100. Based on insights gained from several scholarships, we then initialized the weights for each factor and assigned them to the model. Then we tested the model on different groups of data sets, compared the resulting graphs across groups and modified the parameters accordingly for a large amount of times, until finally the resulting graphs plotted with different groups of data, namely the “scalper index”, match with each other. Eventually, we located the 96% percentile on the graph and acquired the corresponding threshold, because scholarship indicated that the percentage of scalpers among online ticket buyers  is approximately 4%; also, we tested our results from several other outside data.

# Plots
<p align="center">
<img src="https://cloud.githubusercontent.com/assets/8942049/14234240/0f1d429a-f9ac-11e5-9b34-43367966e691.jpg" width="200"/>
<img src="https://cloud.githubusercontent.com/assets/8942049/14234244/2c7c9fde-f9ac-11e5-8110-eaddc5f06dc1.jpg" width="200"/>
<img src="https://cloud.githubusercontent.com/assets/8942049/14234245/2f6d7cd6-f9ac-11e5-8f27-daf007a0f5ad.jpg" width="200"/>
<img src="https://cloud.githubusercontent.com/assets/8942049/14234247/323490f8-f9ac-11e5-89cf-b6581aeee013.jpg" width="200"/>
</p>
<p align="center">
<img src="https://cloud.githubusercontent.com/assets/8942049/14234248/342e8580-f9ac-11e5-884a-8a0bcb8a5e62.jpg" width="200"/>
</p>

# Work cited:
Europe Economics Chancery House, “Analysis of the Secondary Sales Market for Tickets for Sporting, Cultural and other Events”, 14 September 2009,online
