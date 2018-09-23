package com.cootek.gz.arruler

import android.content.Intent
import android.icu.text.DecimalFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java!!.getSimpleName()
    private var arFragment: ArFragment? = null

    private var dataArray = ArrayList<AnchorInfoBean>()

    private val decimalFormat = DecimalFormat("0.00")

    private var lineNodeArray = ArrayList<Node>()

    private var points = ArrayList<CustomPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment?

        reset.setOnClickListener{

        }

        calc.setOnClickListener {
            var intent = Intent()
            intent.setClass(calc.context, AreaActivity::class.java)
            intent.putExtra("points",  points);
            startActivity(intent)

        }


        arFragment?.setOnTapArPlaneListener{hitResult, plane, motionEvent ->
            val aib = AnchorInfoBean("", hitResult.createAnchor(), 0.0)
            dataArray.add(aib)

            var point = CustomPoint(aib.anchor.pose.tx(),aib.anchor.pose.ty(),aib.anchor.pose.tz())
            points.add(point)

            Toast.makeText(this, "this is ${dataArray.size} point", Toast.LENGTH_SHORT).show()

            if (dataArray.size > 1) {
                val start = dataArray.get(dataArray.size - 2).anchor
                val end = dataArray.get(dataArray.size - 1).anchor


                // connect two anchor
                connect(start, end)

                // calc two anchor
                val startPose = start.pose
                val endPose = end.pose

                val delta_x = startPose.tx() - endPose.tx()
                val delta_y = startPose.ty() - endPose.ty()
                val delta_z = startPose.tz() - endPose.tz()

                aib.length = Math.sqrt((delta_x*delta_x + delta_y * delta_y + delta_z + delta_z).toDouble())

                aib.dataText = "the ${dataArray.size -1} line is ${decimalFormat.format(aib.length)}"

            } else {
                aib.dataText = "the first point"

            }
        }

    }

    private fun connect(start: Anchor, end:Anchor) {
        val startNode = AnchorNode(start)
        val endNode = AnchorNode(end)

        startNode.setParent(arFragment?.arSceneView?.scene)

        val startWorldPosition = startNode.worldPosition
        val endWorldPosition = endNode.worldPosition

        val difference = Vector3.subtract(startWorldPosition, endWorldPosition)
        val directionFromTopToBottom = difference.normalized()
        val rotationFromAToB = Quaternion.lookRotation(directionFromTopToBottom, Vector3.up())

        MaterialFactory.makeOpaqueWithColor(this@MainActivity, com.google.ar.sceneform.rendering.Color(0f, 191f, 255f))
                .thenAccept { material ->
                    val lineMode = ShapeFactory.makeCube(Vector3(0.01f, 0.01f, difference.length()), Vector3.zero(), material)
                    val lineNode = Node()
                    lineNode.setParent(startNode)
                    lineNode.renderable = lineMode
                    lineNode.worldPosition = Vector3.add(startWorldPosition, endWorldPosition).scaled(0.5f)
                    lineNode.worldRotation = rotationFromAToB
                    lineNodeArray.add(lineNode)
                }


    }

}
