(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimeDeleteController',TimeDeleteController);

    TimeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Time'];

    function TimeDeleteController($uibModalInstance, entity, Time) {
        var vm = this;

        vm.time = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Time.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
