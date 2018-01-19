(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AcademicDegreeController', AcademicDegreeController);

    AcademicDegreeController.$inject = ['AcademicDegree', 'AcademicDegreeSearch'];

    function AcademicDegreeController(AcademicDegree, AcademicDegreeSearch) {

        var vm = this;

        vm.academicDegrees = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            AcademicDegree.query(function(result) {
                vm.academicDegrees = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AcademicDegreeSearch.query({query: vm.searchQuery}, function(result) {
                vm.academicDegrees = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
