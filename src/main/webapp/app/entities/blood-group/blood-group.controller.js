(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BloodGroupController', BloodGroupController);

    BloodGroupController.$inject = ['BloodGroup', 'BloodGroupSearch'];

    function BloodGroupController(BloodGroup, BloodGroupSearch) {

        var vm = this;

        vm.bloodGroups = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            BloodGroup.query(function(result) {
                vm.bloodGroups = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            BloodGroupSearch.query({query: vm.searchQuery}, function(result) {
                vm.bloodGroups = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
