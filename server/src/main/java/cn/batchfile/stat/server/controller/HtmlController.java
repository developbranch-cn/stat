package cn.batchfile.stat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.batchfile.stat.server.domain.container.ContainerInstance;
import cn.batchfile.stat.server.domain.node.Node;
import cn.batchfile.stat.server.service.ContainerService;
import cn.batchfile.stat.server.service.NodeService;

@Controller
public class HtmlController {
	
	@Autowired
	private ContainerService containerService;
	
	@Autowired
	private NodeService nodeService;
	
	@RequestMapping("/node")
	public String nodeList() {
		return "node-list";
	}
	
	@RequestMapping("/node/{nodeId}")
	public String node(@PathVariable("nodeId") String nodeId, ModelMap model) {
		model.addAttribute("nodeId", nodeId);
		return "node-view";
	}

	@RequestMapping("/node/{nodeId}/container")
	public String nodeContainer(@PathVariable("nodeId") String nodeId, ModelMap model) {
		model.addAttribute("nodeId", nodeId);
		return "node-container";
	}
	
	@RequestMapping("/container/{containerId}")
	public String containerView(@PathVariable("containerId") String containerId, ModelMap model) {
		model.addAttribute("containerId", containerId);
		return "container-view";
	}
	
	@RequestMapping("/container/{containerId}/terminal")
	public String containerTerminal(@PathVariable("containerId") String containerId, ModelMap model) {
		TerminalSocketHandler.nodeService = nodeService;
		
		ContainerInstance container = containerService.getContainer(containerId);
		Node node = nodeService.getNode(container.getNode());
		
		model.addAttribute("container", container);
		model.addAttribute("node", node);
		return "container-terminal";
	}
	
	@RequestMapping("/container/{containerId}/log")
	public String containerLog(@PathVariable("containerId") String containerId, ModelMap model) {
		ContainerInstance container = containerService.getContainer(containerId);
		Node node = nodeService.getNode(container.getNode());
		
		model.addAttribute("container", container);
		model.addAttribute("node", node);
		return "container-log";
	}
}