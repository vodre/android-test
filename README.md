# Android Trendings

##### Livedata + viewmodel + retrofit (all together)
##### Lottie animations
##### CoordinatorLayout, ConstraintLayout

![device](https://media.giphy.com/media/1iplreyP9DgEkAl6h1/giphy.gif)


## Network calls using view model + live data

```viewModel.repositories.observe(this, Observer { repoResource ->
            when (repoResource?.status) {
                Status.LOADING -> {
                    progressbar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressbar.visibility = View.GONE
                    showErrorDialog(errorMessage)
                }
                Status.SUCCESS -> {
                    progressbar.visibility = View.GONE
                    setRepositoryList(data)
                }
            }
        })


