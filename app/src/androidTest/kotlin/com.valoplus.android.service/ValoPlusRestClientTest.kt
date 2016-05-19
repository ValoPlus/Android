
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.SmallTest
import com.valoplus.android.service.rest.RetrofitClient
import com.valoplus.android.service.rest.ValoPlusRestClient
import de.valoplus.dto.InitRequestDTO
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ValoPlusRestClientTest {

    lateinit var restClient: ValoPlusRestClient

    val clientId: String = "7882ABD9-B905-4ABB-BC90-4E71DE8CC9E4"
    val key: String = "123456789abc"

    @Before
    fun init() {
        restClient = RetrofitClient.getValoPlusClientForUrl("test.valoplus.de:9000")
    }

    @Test
    fun testGetSettings() {
        val call = restClient.getSettings(clientId)

//        call.enqueue(object : Callback<ControllerResponseDTO> {
//            override fun onResponse(call: Call<ControllerResponseDTO>?, response: Response<ControllerResponseDTO>?) {
//                assertEquals("mock", response?.body()?.controllerAlias)
//            }
//
//            override fun onFailure(call: Call<ControllerResponseDTO>?, t: Throwable?) {
//                fail()
//            }
//        })

        var response = call.execute()
        assertEquals("controller1", response?.body()?.controllerAlias)
    }

    @Test
    fun testPostInit() {
        val request = InitRequestDTO()

        request.clientId = clientId
        request.key = key

        val call = restClient.postInit(request)

        val response = call.execute()
        assertTrue(response.isSuccessful)
    }
}
