(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimersDialogController', TimersDialogController);

    TimersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Timers'];

    function TimersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Timers) {
        var vm = this;

        vm.timers = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timers.id !== null) {
                Timers.update(vm.timers, onSaveSuccess, onSaveError);
            } else {
                Timers.save(vm.timers, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:timersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
