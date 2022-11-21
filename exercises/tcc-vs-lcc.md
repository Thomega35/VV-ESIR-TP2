# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

TCC and LCC will have the same value if the numbers of indirect connected methods is equal to 0.

TCC = NDC / NP
NDC : number of direct connections

LCC = (NDC + NIC) / NP
NIC : number of indirect connections

If NIC = 0 then LCC = NDC / NP = TCC

Still according this formula TCC <= LCC because the factor NIC can only increase the result. So in the worst case their values will be the same.