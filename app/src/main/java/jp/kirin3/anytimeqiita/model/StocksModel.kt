package jp.kirin3.anytimeqiita.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.StocksResponseData

class StocksModel() : ViewModel() {

    companion object {

        var parcelable: Parcelable? = null

        private var cacheStocksList: List<StocksResponseData>? = null

        fun setStocksToCache(stocksList: List<StocksResponseData>?) {
            cacheStocksList = stocksList
        }

    }
}