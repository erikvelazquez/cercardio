(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimersDeleteController',TimersDeleteController);

    TimersDeleteController.$inject = ['$uibModalInstance', 'entity', 'Timers'];

    function TimersDeleteController($uibModalInstance, entity, Timers) {
        var vm = this;

        vm.timers = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Timers.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
